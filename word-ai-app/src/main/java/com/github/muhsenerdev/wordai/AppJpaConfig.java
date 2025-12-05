package com.github.muhsenerdev.wordai;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = { "com.github.muhsenerdev" })
@EntityScan(basePackages = { "com.github.muhsenerdev" })
public class AppJpaConfig {

}
