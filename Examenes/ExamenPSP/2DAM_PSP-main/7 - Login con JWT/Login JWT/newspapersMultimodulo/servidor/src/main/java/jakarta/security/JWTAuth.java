package jakarta.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.vavr.control.Either;
import jakarta.common.RestConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.miutils.common.AplicationConstants;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {

    @Inject
    private DatabaseIdentityStore identity;

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
            //si vino algo, se separa en dos partes (BASIC/BEARER/...) (Authorization)
            String[] valores = header.split(RestConstants.SPACE);

            if (valores[0].equalsIgnoreCase(RestConstants.BASIC)) {
                c = validarCredencialBasic(httpServletResponse, valores);
            } else if (valores[0].equalsIgnoreCase(RestConstants.BEARER)) {
                Either<Integer, CredentialValidationResult> tryToken = tryValidateToken(httpServletResponse, valores);
                if (tryToken.isLeft()) {
                    httpMessageContext.setResponse(httpServletResponse);
                    return AuthenticationStatus.SEND_FAILURE;
                    // produces con named
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

    private CredentialValidationResult validarCredencialBasic(HttpServletResponse httpServletResponse, String[] valores) {
        CredentialValidationResult c;
        //si es basic, se valida con el identity store pasandole el credential
        if (new BasicAuthenticationCredential(valores[1]).getCaller().equals(AplicationConstants.INVITADO)) {
            c = new CredentialValidationResult(AplicationConstants.INVITADO, Collections.emptySet());
        }
        else {
            c = identity.validate(new BasicAuthenticationCredential(valores[1]));
        }
        // si es valido, se genera un token
        if (c.getStatus() == CredentialValidationResult.Status.VALID) {
            //generar token
            String jws = generateToken(c);

            //se agrega el token al header
            httpServletResponse.addHeader(AplicationConstants.JWT, jws);
        }
        return c;
    }

    private String generateToken(CredentialValidationResult c) {
        return Jwts.builder()
                .setSubject(AplicationConstants.JWT)
                .setExpiration(Date
                        .from(LocalDateTime.now().plusSeconds(60).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim(RestConstants.USER, c.getCallerPrincipal().getName())
                .claim(RestConstants.ROLES, c.getCallerGroups())
                .signWith(key).compact();

    }

}
