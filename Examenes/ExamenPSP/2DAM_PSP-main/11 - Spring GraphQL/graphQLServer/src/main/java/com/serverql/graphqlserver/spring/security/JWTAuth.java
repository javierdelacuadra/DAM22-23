package com.serverql.graphqlserver.spring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.vavr.control.Either;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JWTAuth extends OncePerRequestFilter {

    //Clase utils que validará las credenciales Basic
    private final JWTUtils jwtUtils;

    //Clave secreta para firmar el token
    private final Key key;

    public JWTAuth(@Qualifier("KEY_PROVIDER") Key key, JWTUtils jwtUtils) {
        this.key = key;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            final String[] valores = header.split(" ");
            // VEMOS SI VIENE AUTHORIZATION BASIC O BEARER
            if (valores[0].equals("Bearer")) {
                validarCredencialBearer(request, response, chain, valores);
            } else if (valores[0].equals("Basic")) {
                validarCredencialesBasic(request, response, chain, valores);
            }
        } else {
            //Seguimos con la petición para devolver error personalizado con GraphQL
            doFilter(request, response, chain);
        }
    }

    private void validarCredencialesBasic(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String[] valores) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication;
        //AUTHORIZATION BASIC
        String credenciales = new String(Base64.getUrlDecoder().decode(valores[1]), StandardCharsets.UTF_8);
        //SEPARO EL USUARIO Y LA CONTRASEÑA
        String[] credencialesArray = credenciales.split(":");
        //Creo un objeto UsernamePasswordAuthenticationToken con las credenciales
        if (credencialesArray.length == 2) {
            UsernamePasswordAuthenticationToken usuarioBasic = new UsernamePasswordAuthenticationToken(credencialesArray[0], credencialesArray[1]);
            //Valido las credenciales en la clase utils
            Either<Integer, UsernamePasswordAuthenticationToken> validatedUser = jwtUtils.validate(usuarioBasic);
            if (validatedUser.isRight()) {
                //Si las credenciales son correctas genero el token
                String jws = generateToken(validatedUser.get());
                response.addHeader("JWT", jws);
                //Me guardo el usuario en el contexto de seguridad en la variable authentication
                authentication = validatedUser.get();
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Añadimos el usuario al contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //Continuamos con la petición
                chain.doFilter(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            //Credenciales incorrectas
            chain.doFilter(request, response);
        }
    }

    private void validarCredencialBearer(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String[] valores) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication;
        //AUTHORIZATION BEARER
        Either<Integer, UsernamePasswordAuthenticationToken> isTokenValid = tryValidateToken(valores[1]);
        if (isTokenValid.isRight()) {
            //Vino un usuario, lo guardo en el contexto de seguridad
            authentication = isTokenValid.get();
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            //Si el token no es válido devolvemos el código de error y cortamos la petición
            response.setStatus(isTokenValid.getLeft());
        }
    }

    private String generateToken(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return Jwts.builder()
                .setSubject("JWT")
                .setExpiration(Date
                        //TODO: Cambiar a 15 minutos
                        .from(LocalDateTime.now().plusSeconds(10).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim("user", usernamePasswordAuthenticationToken.getPrincipal())
                .claim("roles", usernamePasswordAuthenticationToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(key).compact();
    }


    private Either<Integer, UsernamePasswordAuthenticationToken> tryValidateToken(String credencial) {
        try {
            //Parseamos el token
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(credencial);
            //Obtenemos el nombre del usuario y sus roles
            String user = claimsJws.getBody().get("user").toString();
            List<String> roles = claimsJws.getBody().get("roles", List.class);
            if (user != null) {
                // Si hay usuario, devolvemos el usuario y sus roles
                return Either.right(new UsernamePasswordAuthenticationToken(
                        //Guardamos el nombre
                        user,
                        //No guaramos sus credenciales
                        null,
                        //Guardamos sus roles
                        roles.stream().map(SimpleGrantedAuthority::new).toList()
                ));
            } else {
                // Si no hay usuario, devolvemos un error 400
                return Either.left(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (ExpiredJwtException e) {
            // return expired, devolvemos un error 409
            return Either.left(HttpServletResponse.SC_REQUEST_TIMEOUT);
        } catch (Exception e) {
            // return invalid, devolvemos un error 400
            return Either.left(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


}
