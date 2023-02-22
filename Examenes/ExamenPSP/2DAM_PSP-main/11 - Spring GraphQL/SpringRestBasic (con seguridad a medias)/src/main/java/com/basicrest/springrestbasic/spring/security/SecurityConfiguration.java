package com.basicrest.springrestbasic.spring.security;


import com.basicrest.springrestbasic.spring.errores.ControlErrores;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)
public class SecurityConfiguration {
    @Qualifier("delegatedAuthenticationEntryPoint")
    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final JWTAuth jwtTokenFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //Desactivamos el csrf para hacer peticiones POST, PUT, DELETE
                .csrf().disable()
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        //Todos el mundo pueden acceder a la ruta de newspapers
                        .requestMatchers("/api/newspapers").permitAll()
                        //Solo los usuarios con rol ADMIN pueden acceder a la ruta de newspapers/id
                        .requestMatchers("/api/newspapers/{id}").hasRole("ADMIN")
                        //Todos los usuarios pueden acceder al resto de rutas
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        //Indicamos la ruta de login, que será pública
                        .loginPage("/api/articles")
                        .permitAll())

                //Añadimos el filtro antes de procesar la petición, por defecto se hará para todas las rutas
                .addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class)

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(new ControlErrores())
                )
                .build();
    }
}
