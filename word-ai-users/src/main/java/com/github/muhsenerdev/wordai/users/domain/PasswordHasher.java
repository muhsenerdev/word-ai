package com.github.muhsenerdev.wordai.users.domain;

public interface PasswordHasher {

    public String hash(String password) throws PasswordHashingException, IllegalArgumentException;

}
