package com.github.muhsenerdev.wordai.words.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.muhsenerdev.wordai.words.domain.WordFactory;
import com.github.slugify.Slugify;

@Configuration
public class WordBeanConfig {

	@Bean
	public WordFactory wordFactory(Slugify slugify) {
		return new WordFactory(slugify);
	}

}
