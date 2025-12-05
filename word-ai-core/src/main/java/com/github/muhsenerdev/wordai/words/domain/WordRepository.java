package com.github.muhsenerdev.wordai.words.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, WordId> {

	@Query("SELECT w FROM Word w LEFT JOIN FETCH w.cards WHERE w.id = :id")
	Optional<Word> findWithCardsById(WordId id);

}
