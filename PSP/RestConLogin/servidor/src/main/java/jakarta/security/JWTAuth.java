package jakarta.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {

    @Inject
    private InMemoryIdentityStore identity;

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
                    httpServletRequest.getSession().setAttribute(ConstantesSecurity.CREDENTIAL, c);
                }
            } else if (valores[0].equalsIgnoreCase("Bearer")) {
                String authHeader = httpServletRequest.getHeader("Authorization");
                String token = null;
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
                final MessageDigest digest;
                try {
                    digest = MessageDigest.getInstance("SHA-512");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                digest.update("clave".getBytes(StandardCharsets.UTF_8));
                final SecretKeySpec key2 = new SecretKeySpec(
                        digest.digest(), 0, 64, "AES");
                SecretKey key22 = Keys.hmacShaKeyFor(key2.getEncoded());

                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(key22)
                        .build()
                        .parseClaimsJws(token);
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