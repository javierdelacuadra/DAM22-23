package org.servidor.jakarta.security;

import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.servidor.domain.servicios.ServiciosUser;
import org.utils.domain.modelo.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;


public class DatabaseIdentityStore implements IdentityStore {

    private final ServiciosUser userServices;

    @Override
    public int priority() {
        return 10;
    }


    @Inject
    public DatabaseIdentityStore(ServiciosUser userServices) {
        this.userServices = userServices;
    }


    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof BasicAuthenticationCredential user) {
            try{
                User logged = userServices.login(user.getCaller(), user.getPasswordAsString());

                Set<String> roles = new HashSet<>(Collections.singleton(logged.getRole().toString()));
                // get roles from database by user
                return new CredentialValidationResult(user.getCaller(), roles);
            }  catch (Exception e) {
                return INVALID_RESULT;
            }
        }
        return INVALID_RESULT;
    }

}
