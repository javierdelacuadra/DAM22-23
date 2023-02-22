package com.serverql.graphqlserver.spring.security.di;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class KeyProvider {
    @Bean("KEY_PROVIDER")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
