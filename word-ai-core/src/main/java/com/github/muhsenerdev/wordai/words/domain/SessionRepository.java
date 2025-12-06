package com.github.muhsenerdev.wordai.words.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.muhsenerdev.commons.jpa.UserId;

public interface SessionRepository extends JpaRepository<Session, SessionId> {

	List<Session> findByUserIdAndDateAndStatus(UserId userId, LocalDate now, SessionStatus active);

	@Query("""
			SELECT sw.wordId
			FROM SessionWord sw
			LEFT JOIN  sw.session s
			WHERE s.userId = :userId AND sw.learned = true
			""")
	Set<WordId> findIdsOfAllLearnedWordsForUser(UserId userId);

	int countByUserIdAndDate(UserId userId, LocalDate now);

	boolean existsByUserIdAndDateAndStatus(UserId userId, LocalDate now, SessionStatus active);

}
