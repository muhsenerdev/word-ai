
package com.github.muhsenerdev.wordai.users.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.commons.jpa.Username;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

    Optional<User> findByUsername(Username username);

    boolean existsByUsername(Username username);

}
