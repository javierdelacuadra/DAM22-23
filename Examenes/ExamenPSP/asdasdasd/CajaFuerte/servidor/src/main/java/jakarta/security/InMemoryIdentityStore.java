package jakarta.security;

import domain.servicios.ServiciosLogin;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import modelo.User;

import java.util.Arrays;
import java.util.List;
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
            String password = Arrays.toString(user.getPassword().getValue());
            password = password.substring(1, password.length() - 1).replaceAll(" ", "").replaceAll(",", "");
            User usuario = ServiciosLogin.getLogin(user.getCaller(), password);
            List<String> roles = usuario.getRoles();
            Set<String> rolesSet = Set.copyOf(roles);
            return new CredentialValidationResult(usuario.getNombre(), rolesSet);
        }
        return INVALID_RESULT;
    }
}