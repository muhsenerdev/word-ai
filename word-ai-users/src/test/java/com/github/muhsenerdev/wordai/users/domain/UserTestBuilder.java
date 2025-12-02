package com.github.muhsenerdev.wordai.users.domain;

import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.support.data.TestData;

public class UserTestBuilder {
    private UserId id;
    private Username username;
    private HashedPassword password;

    public UserTestBuilder() {
        this.id = UserId.random();
        this.username = TestData.username();
        this.password = TestData.hashedPassword();
    }

    public static UserTestBuilder aUser() {
        return new UserTestBuilder();
    }

    public static UserTestBuilder from(User user) {
        return aUser()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withPassword(user.getPassword());
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

    public User build() {
        return new User(id, username, password);
    }
}
