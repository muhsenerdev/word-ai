package com.github.muhsenerdev.wordai.users.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.domain.Role;
import com.github.muhsenerdev.wordai.users.domain.User;

@ExtendWith(SpringExtension.class)
@Import(UserMapper.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("Should map User to UserCreationResponse correctly")
    void shouldMapUserToUserCreationResponse() {
        // Given
        // We mock User because its constructor is protected and TestBuilder is having
        // issues
        User user = org.mockito.Mockito.mock(User.class);

        UserId userId = UserId.random();
        Username username = Username.of("testuser");

        org.mockito.Mockito.when(user.getId()).thenReturn(userId);
        org.mockito.Mockito.when(user.getUsername()).thenReturn(username);

        Role role = org.mockito.Mockito.mock(Role.class);
        RoleName roleName = org.mockito.Mockito.mock(RoleName.class);
        org.mockito.Mockito.when(roleName.getValue()).thenReturn("ROLE_USER");
        org.mockito.Mockito.when(role.getName()).thenReturn(roleName);

        org.mockito.Mockito.when(user.getRoles()).thenReturn(java.util.Set.of(role));

        // When
        var response = userMapper.toResponse(user);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(userId.getValue());
        assertThat(response.username()).isEqualTo("testuser");
        assertThat(response.roles()).hasSize(1);
        assertThat(response.roles()).containsExactly("ROLE_USER");
    }

}
