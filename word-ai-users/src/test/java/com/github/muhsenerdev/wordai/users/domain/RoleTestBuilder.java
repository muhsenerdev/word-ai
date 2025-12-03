package com.github.muhsenerdev.wordai.users.domain;

import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.wordai.users.support.data.TestData;

public class RoleTestBuilder {

    private RoleId id;
    private RoleName name;

    public RoleTestBuilder() {
        this.id = RoleId.random();
        this.name = TestData.randomRoleName();
    }

    public static RoleTestBuilder aRole() {
        return new RoleTestBuilder();
    }

    public static RoleTestBuilder from(Role role) {
        return aRole()
                .withId(role.getId())
                .withName(role.getName());
    }

    public RoleTestBuilder withId(RoleId id) {
        this.id = id;
        return this;
    }

    public RoleTestBuilder withName(RoleName name) {
        this.name = name;
        return this;
    }

    public Role build() {
        return new Role(id, name);
    }
}
