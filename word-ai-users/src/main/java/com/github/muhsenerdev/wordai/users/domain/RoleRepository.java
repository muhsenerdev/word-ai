package com.github.muhsenerdev.wordai.users.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.muhsenerdev.commons.jpa.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleId> {

    Optional<Role> findByName(RoleName name);

}
