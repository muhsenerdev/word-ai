
package com.github.muhsenerdev.wordai.users.domain;

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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import lombok.Getter;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
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

    protected User(UserId id, Username username, HashedPassword password) {
        this.id = id;
        this.username = username;
        this.password = password;

        DomainUtils.notNull(id, "Id cannot be null", "user.id.required");
        DomainUtils.notNull(username, "Username cannot be null", "user.username.required");
        DomainUtils.notNull(password, "Password cannot be null", "user.password.required");
    }

    @Builder(access = AccessLevel.PROTECTED)
    protected static User of(Username username, HashedPassword password) {
        return new User(UserId.random(), username, password);
    }

    @Override
    public UserId getId() {
        return id;
    }

}
