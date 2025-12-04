package com.github.muhsenerdev.wordai.users.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.domain.PasswordFactory;
import com.github.muhsenerdev.wordai.users.domain.Role;
import com.github.muhsenerdev.wordai.users.domain.RoleRepository;

import com.github.muhsenerdev.wordai.users.domain.UserFactory;
import com.github.muhsenerdev.wordai.users.domain.UserRepository;
import com.github.muhsenerdev.wordai.users.support.data.TestData;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class UserApplicationServiceImplTest {

    @Mock
    private UserFactory userFactory;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordFactory passwordFactory;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserApplicationServiceImpl userApplicationService;

    @Test
    @DisplayName("Should create user successfully when username is unique and role exists")
    void shouldCreateUserSuccessfully() {
        // Given
        var command = TestData.createUserCommand();
        var username = Username.of(command.username());
        var rawPassword = TestData.rawPassword();
        var hashedPassword = TestData.hashedPassword();
        var role = Role.of(RoleName.of("USER"));
        var user = com.github.muhsenerdev.wordai.users.domain.UserTestBuilder.aUser()
                .withUsername(username)
                .withPassword(hashedPassword)
                .withRoles(Set.of(role))
                .build();
        var expectedResponse = TestData.userCreationResponse();

        givenUserDoesNotExist(username);
        givenRoleExists(role);
        givenPasswordCreation(command.password(), rawPassword);
        givenUserCreation(user);
        givenUserMapping(user, expectedResponse);

        // When
        var response = userApplicationService.createUser(command);

        // Then
        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verifyUserExistenceCheck(username);
        verifyRoleLookup();
        verifyPasswordCreation(command.password());
        verifyUserCreation(username);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    @DisplayName("Should throw DuplicateUserException when username already exists")
    void shouldThrowDuplicateUserException() {
        // Given
        var command = TestData.createUserCommand();
        var username = Username.of(command.username());

        givenUserExists(username);

        // When & Then
        assertThrows(DuplicateUserException.class, () -> userApplicationService.createUser(command));

        verifyUserExistenceCheck(username);
    }

    @Test
    @DisplayName("Should throw SystemException when default role is not found")
    void shouldThrowSystemExceptionWhenRoleNotFound() {
        // Given
        var command = TestData.createUserCommand();
        var username = Username.of(command.username());

        givenUserDoesNotExist(username);
        givenRoleDoesNotExist();

        // When & Then
        assertThrows(com.github.muhsenerdev.commons.core.exception.SystemException.class,
                () -> userApplicationService.createUser(command));

        verifyUserExistenceCheck(username);
        verifyRoleLookup();
    }

    @Test
    @DisplayName("Should throw BusinessValidationException when DomainException occurs")
    void shouldThrowBusinessValidationExceptionWhenDomainExceptionOccurs() {
        // Given
        var command = TestData.createUserCommand();
        var username = Username.of(command.username());
        var rawPassword = TestData.rawPassword();
        var role = Role.of(RoleName.of("USER"));

        givenUserDoesNotExist(username);
        givenRoleExists(role);
        givenPasswordCreation(command.password(), rawPassword);
        givenUserCreationThrowsDomainException();

        // When & Then
        assertThrows(com.github.muhsenerdev.commons.core.exception.BusinessValidationException.class,
                () -> userApplicationService.createUser(command));

        verifyUserExistenceCheck(username);
        verifyRoleLookup();
        verifyPasswordCreation(command.password());
        verifyUserCreation(username);
    }

    private void givenUserDoesNotExist(Username username) {
        when(userRepository.existsByUsername(username)).thenReturn(false);
    }

    private void givenUserExists(Username username) {
        when(userRepository.existsByUsername(username)).thenReturn(true);
    }

    private void givenRoleExists(Role role) {
        when(roleRepository.findByName(any(RoleName.class))).thenReturn(Optional.of(role));
    }

    private void givenRoleDoesNotExist() {
        when(roleRepository.findByName(any(RoleName.class))).thenReturn(Optional.empty());
    }

    private void givenPasswordCreation(String password,
            com.github.muhsenerdev.wordai.users.domain.RawPassword rawPassword) {
        when(passwordFactory.createRawPassword(password)).thenReturn(rawPassword);
    }

    private void givenUserCreation(com.github.muhsenerdev.wordai.users.domain.User user) {
        when(userFactory.create(any(Username.class), any(com.github.muhsenerdev.wordai.users.domain.RawPassword.class),
                any())).thenReturn(user);
    }

    private void givenUserCreationThrowsDomainException() {
        when(userFactory.create(any(Username.class), any(com.github.muhsenerdev.wordai.users.domain.RawPassword.class),
                any()))
                .thenThrow(new com.github.muhsenerdev.commons.core.DomainException("Domain error", "error.code") {
                });
    }

    private void givenUserMapping(com.github.muhsenerdev.wordai.users.domain.User user, UserCreationResponse response) {
        when(userMapper.toResponse(user)).thenReturn(response);
    }

    private void verifyUserExistenceCheck(Username username) {
        verify(userRepository).existsByUsername(username);
    }

    private void verifyRoleLookup() {
        verify(roleRepository).findByName(RoleName.of("USER"));
    }

    private void verifyPasswordCreation(String password) {
        verify(passwordFactory).createRawPassword(password);
    }

    private void verifyUserCreation(Username username) {
        verify(userFactory).create(any(Username.class),
                any(com.github.muhsenerdev.wordai.users.domain.RawPassword.class), any());
    }
}
