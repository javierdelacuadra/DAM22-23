package jakarta.security;

import domain.servicios.impl.LoginServicesImpl;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.miutils.domain.modelo.Reader;

import java.util.HashSet;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;


public class DatabaseIdentityStore implements IdentityStore {

    private final LoginServicesImpl loginServices;

    @Override
    public int priority() {
        return 10;
    }


    @Inject
    public DatabaseIdentityStore(LoginServicesImpl loginServices) {
        this.loginServices = loginServices;
    }


    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof BasicAuthenticationCredential user) {
            try{
                Reader logged = loginServices.login(user.getCaller(), user.getPasswordAsString());

                Set<String> roles = new HashSet<>(loginServices.getRoles(logged.getId()));
                user.getPassword().getValue();
                // get roles from database by user
                return new CredentialValidationResult(user.getCaller(), roles);
            }  catch (Exception e) {
                return INVALID_RESULT;
            }
        }
        return INVALID_RESULT;
    }

}
