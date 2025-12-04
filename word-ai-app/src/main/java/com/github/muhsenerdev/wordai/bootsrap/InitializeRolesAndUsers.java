
package com.github.muhsenerdev.wordai.bootsrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Configuration;

import com.github.muhsenerdev.commons.jpa.RoleName;
import com.github.muhsenerdev.commons.jpa.Username;
import com.github.muhsenerdev.wordai.users.domain.PasswordFactory;
import com.github.muhsenerdev.wordai.users.domain.RawPassword;
import com.github.muhsenerdev.wordai.users.domain.Role;
import com.github.muhsenerdev.wordai.users.domain.RoleRepository;
import com.github.muhsenerdev.wordai.users.domain.User;
import com.github.muhsenerdev.wordai.users.domain.UserFactory;
import com.github.muhsenerdev.wordai.users.domain.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("null")
public class InitializeRolesAndUsers {

    private final BootstrapAdminProps props;
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final UserFactory userFactory;
    private final PasswordFactory passwordFactory;

    @PostConstruct
    public void init() {
        Map<RoleName, Role> roleMap = initRoles();
        initAdmin(roleMap);

    }

    private void initAdmin(Map<RoleName, Role> roleMap) {
        try {
            if (userRepo.count() == 0) {
                checkAdminProps(props);
                RawPassword rawPassword = passwordFactory.createRawPasswordWithNoValidation(props.getPassword());
                Username username = Username.of(props.getUsername());
                Role role = roleMap.get(RoleName.of("ADMIN"));
                User user = userFactory.create(username, rawPassword, List.of(role));
                userRepo.save(user);
                log.info("Admin user created.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize admin user, due to: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unused")
    private void checkAdminProps(BootstrapAdminProps props) {

        String usernameString = props.getUsername();
        Username username = Username.of(usernameString);

        String passwordString = props.getPassword();
        RawPassword password = passwordFactory.createRawPasswordWithNoValidation(passwordString);
    }

    private Map<RoleName, Role> initRoles() {
        try {
            Optional<Role> adminOptional = roleRepo.findByName(RoleName.of("ADMIN"));
            Optional<Role> userOptional = roleRepo.findByName(RoleName.of("USER"));

            RoleName adminName = RoleName.of("ADMIN");
            RoleName userName = RoleName.of("USER");
            Map<RoleName, Role> map = new HashMap<>();
            if (adminOptional.isEmpty()) {
                Role roleAdmin = Role.of(adminName);
                roleAdmin = roleRepo.save(roleAdmin);
                log.info("Role ADMIN created.");
                map.put(adminName, roleAdmin);
            } else {
                map.put(adminName, adminOptional.get());
            }

            if (userOptional.isEmpty()) {
                Role roleUser = Role.of(userName);
                roleUser = roleRepo.save(roleUser);
                log.info("Role USER created.");
                map.put(userName, roleUser);
            } else {
                map.put(userName, userOptional.get());
            }

            return map;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize roles, due to: " + e.getMessage(), e);
        }

    }

}
