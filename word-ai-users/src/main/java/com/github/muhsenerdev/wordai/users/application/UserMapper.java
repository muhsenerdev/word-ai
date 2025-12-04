package com.github.muhsenerdev.wordai.users.application;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import com.github.muhsenerdev.wordai.users.domain.Role;
import com.github.muhsenerdev.wordai.users.domain.User;

@Mapper(componentModel = "spring", uses = { CoreMapper.class })
public interface UserMapper {

    UserCreationResponse toResponse(User user);

    default String fromRoleToString(Role role) {
        return role == null ? null : role.getName().getValue();
    }

    default Set<String> fromRolesToStrings(Set<Role> roles) {
        if (roles == null) {
            return Set.of();
        }
        return roles.stream()
                .map(this::fromRoleToString)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
