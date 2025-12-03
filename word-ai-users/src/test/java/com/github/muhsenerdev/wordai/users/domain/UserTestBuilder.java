package com.github.muhsenerdev.wordai.users.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.support.data.TestData;

public class UserTestBuilder {
    private UserId id;
    private Username username;
    private HashedPassword password;
    private Set<Role> roles;

    public UserTestBuilder() {
        this.id = UserId.random();
        this.username = TestData.username();
        this.password = TestData.hashedPassword();
        this.roles = new HashSet<>();
        roles.add(RoleTestBuilder.aRole().build());
    }

    public static UserTestBuilder aUser() {
        return new UserTestBuilder();
    }

    public static UserTestBuilder from(User user) {
        return aUser()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .withRoles(user.getRoles());
    }

    public UserTestBuilder withId(UserId id) {
        this.id = id;
        return this;
    }

    public UserTestBuilder withUsername(Username username) {
        this.username = username;
        return this;
    }

    public UserTestBuilder withPassword(HashedPassword password) {
        this.password = password;
        return this;
    }

    public UserTestBuilder withRoles(Set<Role> roles) {
        this.roles = new HashSet<>(roles);
        return this;
    }

    public UserTestBuilder withRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public User build() {
        return new User(id, username, password, Collections.unmodifiableSet(roles));
    }
}
