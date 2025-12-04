package com.github.muhsenerdev.wordai.words.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.muhsenerdev.commons.jpa.UserId;

@Repository
public interface LearnerRepository extends JpaRepository<Learner, UserId> {

    Optional<Learner> findByUserId(UserId userId);

}
