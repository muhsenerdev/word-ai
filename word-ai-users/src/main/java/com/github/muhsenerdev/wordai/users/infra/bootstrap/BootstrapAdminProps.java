package com.github.muhsenerdev.wordai.users.infra.bootstrap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("bootstrap.admin")
@Data
public class BootstrapAdminProps {

    private String username;
    private String password;

}
