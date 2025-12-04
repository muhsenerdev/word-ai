package com.github.muhsenerdev.wordai.users.application;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.muhsenerdev.commons.core.DomainException;
import com.github.muhsenerdev.commons.core.exception.BusinessValidationException;
import com.github.muhsenerdev.commons.core.exception.DuplicateResourceException;
import com.github.muhsenerdev.commons.core.exception.SystemException;
import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.domain.PasswordFactory;
import com.github.muhsenerdev.wordai.users.domain.RoleRepository;
import com.github.muhsenerdev.wordai.users.domain.UserFactory;
import com.github.muhsenerdev.wordai.users.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserFactory userFactory;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordFactory passwordFactory;
    private final UserMapper userMapper;

    @Override
    @Transactional
    @SuppressWarnings("null")
    public UserCreationResponse createUser(CreateUserCommand command)
            throws DuplicateUserException, BusinessValidationException {
        try {

            // 1. Check the existence of user with the same username.
            var username = Username.of(command.username());
            if (userRepository.existsByUsername(username)) {
                throw new DuplicateUserException(username);
            }

            // 2 Fetch default Role
            var userRole = roleRepository.findByName(RoleName.of("USER"))
                    .orElseThrow(() -> new IllegalStateException("Default role USER not found"));

            // 3. Hash password and create use
            var rawPassword = passwordFactory.createRawPassword(command.password());
            var user = userFactory.create(username, rawPassword, Set.of(userRole));

            // 4. Persist User
            userRepository.save(user);

            // 5. Map User to UserCreationResponse
            return userMapper.toResponse(user);
        } catch (DuplicateResourceException e) {
            throw e;
        } catch (DomainException e) {
            throw new BusinessValidationException(e.getMessage(), e.getErrorCode(), e);
        } catch (Exception e) {
            throw new SystemException("Failed to create user, due to : " + e.getMessage(), "unexpected error", e);
        }

    }
}
