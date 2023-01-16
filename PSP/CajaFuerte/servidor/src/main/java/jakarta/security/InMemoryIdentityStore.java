package jakarta.security;

import domain.servicios.ServiciosLogin;
import jakarta.inject.Inject;
import jakarta.security.common.ConstantesSecurity;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import model.ReaderLogin;

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
            ReaderLogin login = servicios.getLogin(user.getCaller(), user.getPassword().getValue());
            return switch (login.getRole().toLowerCase()) {
                case ConstantesSecurity.ADMIN ->
                        new CredentialValidationResult(login.getUsername(), Set.of(ConstantesSecurity.ADMIN));
                case ConstantesSecurity.USER ->
                        new CredentialValidationResult(login.getUsername(), Collections.singleton(ConstantesSecurity.USER));
                default -> INVALID_RESULT;
            };
        }
        return INVALID_RESULT;
    }
}