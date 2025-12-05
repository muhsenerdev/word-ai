package com.github.muhsenerdev.wordai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.github.muhsenerdev")
public class WordAIApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordAIApplication.class, args);
    }

}
