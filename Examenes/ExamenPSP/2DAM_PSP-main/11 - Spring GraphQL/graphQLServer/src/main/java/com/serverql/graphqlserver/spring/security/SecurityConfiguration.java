package com.serverql.graphqlserver.spring.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    private final JWTAuth jwtTokenFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //Desactivamos el csrf para hacer peticiones POST, PUT, DELETE (si no no va GraphQL)
                .csrf().disable()
                //Añadimos el filtro antes de procesar la petición, por defecto se hará para todas las rutas
                .addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class)
                .build();
    }
}
