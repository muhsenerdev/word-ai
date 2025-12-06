
package com.github.muhsenerdev.wordai.users.infra.security;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.domain.Role;
import com.github.muhsenerdev.wordai.users.domain.User;
import com.github.muhsenerdev.wordai.users.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findWithRolesByUsername(Username.of(username));
		if (userOptional.isEmpty())
			throw new UsernameNotFoundException("User not found");

		User user = userOptional.get();

		return DefaultPrincipal.builder().hashedPassword(user.getPassword()).username(user.getUsername())
				.userId(user.getId()).roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
				.build();

	}

}
