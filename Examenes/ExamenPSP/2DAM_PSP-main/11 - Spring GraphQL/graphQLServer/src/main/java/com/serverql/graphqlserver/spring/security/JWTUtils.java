package com.serverql.graphqlserver.spring.security;

import com.serverql.graphqlserver.domain.modelo.Login;
import com.serverql.graphqlserver.domain.servicios.ServiciosLogin;
import io.vavr.control.Either;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class JWTUtils {

    private final ServiciosLogin serviciosLogin;

    public JWTUtils(ServiciosLogin serviciosLogin) {
        this.serviciosLogin = serviciosLogin;
    }
    public Either<Integer, UsernamePasswordAuthenticationToken> validate(UsernamePasswordAuthenticationToken usuarioBasic)
    {
        //VALIDO USUARIO Y CONTRASEÑA CONTRA LA BD
        Login l = serviciosLogin.login(usuarioBasic.getName(), usuarioBasic.getCredentials().toString());
        if (l != null) {
            //Si es valido, cargo usuario y rol
            return Either.right(new UsernamePasswordAuthenticationToken(
                    l.getUsername(),
                    null,
                    Stream.of(new SimpleGrantedAuthority("ROLE_"+l.getRole())).toList()
            ));
        }
        else {
//            Si no es valido, devuelvo un 401
            return Either.left(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
