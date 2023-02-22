package org.servidor.jakarta.di;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.servidor.jakarta.common.RestConstants;

import java.security.Key;

public class KeyProvider {
    // Al ser un singleton, se generará una clave aleatoria por cada vez que se ejecute el servidor
    @Produces
    @Singleton
    @Named(RestConstants.KEY_PROVIDER)
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
