
package com.github.muhsenerdev.wordai.users.domain;

import com.github.muhsenerdev.commons.core.DomainUtils;
import com.github.muhsenerdev.commons.jpa.BaseJpaEntity;
import com.github.muhsenerdev.commons.jpa.RoleName;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseJpaEntity<RoleId> {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    private RoleId id;

    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false, unique = true))
    private RoleName name;

    Role(RoleId id, RoleName name) {
        this.id = id;
        this.name = name;

        DomainUtils.notNull(id, "ID of Role cannot be null.", "role.id.required");
        DomainUtils.notNull(name, "Name of Role cannot be null.", "role.name.required");
    }

    public static Role of(RoleName name) {
        return new Role(RoleId.random(), name);
    }

    @Override
    public RoleId getId() {
        return id;
    }

}
