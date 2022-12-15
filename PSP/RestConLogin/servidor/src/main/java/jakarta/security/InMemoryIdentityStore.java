package jakarta.security;

import domain.servicios.ServiciosLogin;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import model.ReaderLogin;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;


public class InMemoryIdentityStore implements IdentityStore {

    private final ServiciosLogin servicios;

    @Override
    public int priority() {
        return 10;
    }

    @Inject
    public InMemoryIdentityStore(ServiciosLogin servicios) {
        this.servicios = servicios;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof BasicAuthenticationCredential user) {
            ReaderLogin login = servicios.getLogin(user.getCaller(), Arrays.toString(user.getPassword().getValue()));
            //TODO: cambiar a login.getRole()
            return switch (user.getCaller()) {
                case "admin" -> new CredentialValidationResult("admin", Set.of("admin"));
                case "usuario" -> new CredentialValidationResult("usuario", Collections.singleton("usuario"));
                default -> INVALID_RESULT;
            };
        }
        return INVALID_RESULT;
    }
}