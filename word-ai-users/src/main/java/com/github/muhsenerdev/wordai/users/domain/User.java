
package com.github.muhsenerdev.wordai.users.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.jpa.SoftDeletableEntity;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.commons.jpa.Username;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import lombok.Getter;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@EqualsAndHashCode(callSuper = true)
public class User extends SoftDeletableEntity<UserId> {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    private UserId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "username", nullable = false))
    private Username username;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password", nullable = false))
    private HashedPassword password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
    private Set<Role> roles = new HashSet<>();

    protected User(UserId id, Username username, HashedPassword password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;

        DomainUtils.notNull(id, "Id cannot be null", "user.id.required");
        DomainUtils.notNull(username, "Username cannot be null", "user.username.required");
        DomainUtils.notNull(password, "Password cannot be null", "user.password.required");
        DomainUtils.notEmpty(roles, "User must have at least one role.", "user.role.at-least-one");

        this.roles.addAll(roles);
    }

    @Builder(access = AccessLevel.PROTECTED)
    protected static User of(Username username, HashedPassword password, Collection<Role> roles) {
        return new User(UserId.random(), username, password, roles != null ? new HashSet<>(roles) : null);
    }

    @Override
    public UserId getId() {
        return id;
    }

}
