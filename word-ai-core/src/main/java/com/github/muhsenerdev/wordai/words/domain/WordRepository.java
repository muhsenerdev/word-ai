package com.github.muhsenerdev.wordai.words.domain;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.muhsenerdev.commons.jpa.UserId;

@Repository
public interface WordRepository extends JpaRepository<Word, WordId> {

	@Query("SELECT w FROM Word w LEFT JOIN FETCH w.cards WHERE w.id = :id")
	Optional<Word> findWithCardsById(WordId id);

	@Query(value = """
			SELECT w.* FROM words w
			WHERE w.language = :#{#language.name()}
			  AND NOT EXISTS (
			    SELECT 1
			    FROM session_words sw
			    INNER JOIN sessions s ON sw.session_id = s.id
			    WHERE sw.word_id = w.id
			      AND s.user_id = :#{#userId.value}
			      AND sw.learned = true
			)
			ORDER BY RANDOM()
			LIMIT :limit
			""", nativeQuery = true)
	Set<Word> findRandomNewWordsForUser(@Param("userId") UserId userId, @Param("language") Language language,
			@Param("limit") int limit);

}
