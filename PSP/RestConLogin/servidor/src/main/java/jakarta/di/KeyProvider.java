package jakarta.di;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.security.common.ConstantesSecurity;

import java.security.Key;

public class KeyProvider {

    @Produces
    @Singleton
    @Named(ConstantesSecurity.JWT)
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
