package jakarta.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.common.ConstantesSecurity;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {

    @Inject
    private InMemoryIdentityStore identity;

    @Inject
    @Named("JWT")
    private Key key;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(ConstantesSecurity.REGEX);

            if (valores[0].equalsIgnoreCase(ConstantesSecurity.BASIC)) {
                c = identity.validate(new BasicAuthenticationCredential(valores[1]));
                if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                    String jws = Jwts.builder()
                            .setSubject("token")
                            .setIssuer("ServidorRest")
                            .setExpiration(Date
                                    .from(LocalDateTime.now().plusSeconds(600).atZone(ZoneId.systemDefault())
                                            .toInstant()))
                            .claim("user", c.getCallerPrincipal().getName())
                            .claim("group", c.getCallerGroups().toArray()[0])
                            .signWith(key).compact();
                    httpServletResponse.addHeader("Authorization", "Bearer " + jws);
                }
            } else if (valores[0].equalsIgnoreCase("Bearer")) {

                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(valores[1]);

                c = new CredentialValidationResult(jws.getBody().get("user").toString(),
                        Set.of(jws.getBody().get("group").toString()));

                httpServletResponse.addHeader("Authorization", "Bearer " + valores[1]);

            }

//            } else if (valores[0].equalsIgnoreCase("Logout")) {
//                httpServletRequest.getSession().removeAttribute(ConstantesSecurity.CREDENTIAL);
//                httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            }

        } else {
            if (httpServletRequest.getSession().getAttribute(ConstantesSecurity.CREDENTIAL) != null)
                c = (CredentialValidationResult) httpServletRequest.getSession().getAttribute(ConstantesSecurity.CREDENTIAL);
        }

        if (c.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(c);
    }
}