package com.github.muhsenerdev.wordai.users.application;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.muhsenerdev.wordai.users.domain.Role;
import com.github.muhsenerdev.wordai.users.domain.User;

@Component
public class UserMapper {

    public UserCreationResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserCreationResponse.builder().id(user.getId() != null ? user.getId().getValue() : null)
                .username(user.getUsername() != null ? user.getUsername().getValue() : null)
                .roles(fromRolesToStrings(user.getRoles())).build();
    }

    private String fromRoleToString(Role role) {
        return role == null || role.getName() == null ? null : role.getName().getValue();
    }

    private Set<String> fromRolesToStrings(Set<Role> roles) {
        if (roles == null) {
            return java.util.Collections.emptySet();
        }
        return roles.stream().map(this::fromRoleToString).filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
