package com.basicrest.springrestbasic.spring.security;

import io.vavr.control.Either;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class JWTUtils {
    public Either<Integer, UsernamePasswordAuthenticationToken> validate(UsernamePasswordAuthenticationToken usuarioBasic)
    {
        //VALIDO USUARIO Y CONTRASEÑA CONTRA LA BD
        if (usuarioBasic.getName().equals("user") && usuarioBasic.getCredentials().equals("password")){
            return Either.right(
                    new UsernamePasswordAuthenticationToken(
                            //Devuelvo el usuario con sus roles, sin la contraseña
                            usuarioBasic.getPrincipal(),
                            null,
                            Stream.of("ROLE_USER").map(SimpleGrantedAuthority::new).toList()
                    )
            );
        }
        else if (usuarioBasic.getName().equals("admin") && usuarioBasic.getCredentials().equals("password")){
            return Either.right(
                    new UsernamePasswordAuthenticationToken(
                            //Devuelvo el usuario con sus roles, sin la contraseña
                              usuarioBasic.getPrincipal(),
                            null,
                            Stream.of("ROLE_ADMIN").map(SimpleGrantedAuthority::new).toList()
                    )
            );
        }
        else {
            //Si no es valido, devuelvo un 401
            return Either.left(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
