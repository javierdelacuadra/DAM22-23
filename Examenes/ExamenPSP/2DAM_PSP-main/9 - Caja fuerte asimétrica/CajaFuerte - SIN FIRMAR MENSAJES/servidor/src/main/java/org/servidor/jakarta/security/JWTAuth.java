package org.servidor.jakarta.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.servidor.jakarta.common.RestConstants;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashSet;

@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {

    @Named(RestConstants.KEY_PROVIDER)
    @Inject
    Key key; // Esta clave viene inyectada desde el KeyProvider

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) {
        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;

        //get la autorizacion del header
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);


        if (header != null) {
            //si vino algo, se separa en dos partes (BEARER/...) (Authorization)
            String[] valores = header.split(RestConstants.SPACE);
            if (valores[0].equalsIgnoreCase(RestConstants.BEARER)) {
                Either<Integer, CredentialValidationResult> tryToken = tryValidateToken(httpServletResponse, valores);
                if (tryToken.isLeft()) {
                    httpMessageContext.setResponse(httpServletResponse);
                    return AuthenticationStatus.SEND_FAILURE;
                } else {
                    c = tryToken.get();
                }
            }
        }
        if (c.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            //no autorizado
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(c);
    }

    private Either<Integer, CredentialValidationResult> tryValidateToken(HttpServletResponse httpServletResponse, String[] valores) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(valores[1]);

            return Either.right(new CredentialValidationResult(jws.getBody().get(RestConstants.USER).toString(), new HashSet<>((ArrayList<String>) jws.getBody().get(RestConstants.ROLES))));
        } catch (ExpiredJwtException e) {
            // return expired
            httpServletResponse.setStatus(HttpServletResponse.SC_REQUEST_TIMEOUT);
            return Either.left(-1);
        } catch (Exception e) {
            // return invalid
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Either.left(0);
        }
    }

}
