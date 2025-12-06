package com.github.muhsenerdev.wordai.users.infra.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.muhsenerdev.commons.core.Assert;
import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.commons.jpa.UserId;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.domain.HashedPassword;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DefaultPrincipal implements UserDetails {
	private final UserId userId;
	private final Set<GrantedAuthority> roles;
	private final Username username;
	private HashedPassword hashedPassword;

	@Builder
	public DefaultPrincipal(UserId userId, Set<RoleName> roles, Username username, HashedPassword hashedPassword) {
		Assert.notNull(userId, "userId cannot be null");
		Assert.notNull(username, "username cannot be null");
		this.userId = userId;
		this.username = username;
		this.hashedPassword = hashedPassword;
		if (roles != null) {
			this.roles = roles.stream().map(role -> new SimpleGrantedAuthority(role.getValue()))
					.collect(Collectors.toSet());
		} else
			this.roles = new HashSet<>();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return hashedPassword.getValue();
	}

	@Override
	public String getUsername() {
		return username.getValue();
	}

}
