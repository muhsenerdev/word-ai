package com.github.muhsenerdev.wordai.users.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.muhsenerdev.wordai.users.domain.UserTestBuilder;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserMapperTest.Config.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("Should map User to UserCreationResponse correctly")
    void shouldMapUserToUserCreationResponse() {
        // Given
        var user = UserTestBuilder.aUser().build();

        // When
        var response = userMapper.toResponse(user);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(user.getId().getValue());
        assertThat(response.username()).isEqualTo(user.getUsername().getValue());
        assertThat(response.roles()).hasSize(user.getRoles().size());
        assertThat(response.roles()).containsExactlyInAnyOrderElementsOf(
                user.getRoles().stream()
                        .map(role -> role.getName().getValue())
                        .toList());
    }

    @TestConfiguration
    static class Config {

        @Bean
        CoreMapper coreMapper() {
            return Mappers.getMapper(CoreMapper.class);
        }

        @Bean
        UserMapper userMapper() {
            return Mappers.getMapper(UserMapper.class);
        }
    }
}
