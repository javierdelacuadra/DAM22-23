package jakarta.security;

import jakarta.common.RestConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.miutils.common.AplicationConstants;

import java.util.Collections;

@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {

    @Inject
    private InMemoryIdentityStore identity;


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
                //si es basic, se valida con el identity store pasandole el credential
                c = identity.validate(new BasicAuthenticationCredential(valores[1]));

                // si es valido, se guarda en la sesion para que no se vuelva a validar
                if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                    httpServletRequest.getSession().setAttribute(RestConstants.USERLOGIN, c);
                    //generar token


                    //añadir al response
                }
            } else if (valores[0].equalsIgnoreCase(AplicationConstants.INVITADO)) {

                c = new CredentialValidationResult(AplicationConstants.INVITADO, Collections.emptySet());
                if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                    httpServletRequest.getSession().setAttribute(RestConstants.USERLOGIN, c);
                }


            } else if (valores[0].equalsIgnoreCase(RestConstants.LOGOUT)) {
                // si hace un logout, se invalida la sesion
                httpServletRequest.getSession().removeAttribute(RestConstants.USERLOGIN);
                httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            // no vino una autorizacion, vemos si se habia hecho previamente
            if (httpServletRequest.getSession().getAttribute(RestConstants.USERLOGIN) != null)
                c = (CredentialValidationResult) httpServletRequest.getSession().getAttribute(RestConstants.USERLOGIN);

            //autorizado
        }
        if (c.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            //no autorizado
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(c);
    }
}
