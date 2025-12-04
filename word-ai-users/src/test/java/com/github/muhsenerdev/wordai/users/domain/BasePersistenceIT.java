// package com.github.muhsenerdev.wordai.users.domain;

// import
// org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import
// org.springframework.boot.testcontainers.service.connection.ServiceConnection;
// import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// import org.springframework.test.context.ActiveProfiles;
// import org.testcontainers.containers.PostgreSQLContainer;
// import org.testcontainers.junit.jupiter.Testcontainers;

// @Testcontainers
// @DataJpaTest
// @EnableJpaAuditing
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @ActiveProfiles("test")
// public abstract class BasePersistenceIT {

// @ServiceConnection
// @SuppressWarnings("resource")
// static PostgreSQLContainer<?> postgres = new
// PostgreSQLContainer<>("postgres:17")
// .withDatabaseName("testdb")
// .withUsername("sa")
// .withPassword("sa");

// }
// //