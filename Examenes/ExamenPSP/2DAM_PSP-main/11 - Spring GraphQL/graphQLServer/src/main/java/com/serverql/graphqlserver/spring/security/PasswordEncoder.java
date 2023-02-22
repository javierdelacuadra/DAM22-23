package com.serverql.graphqlserver.spring.security;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Getter
public class PasswordEncoder {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoder (BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

}

