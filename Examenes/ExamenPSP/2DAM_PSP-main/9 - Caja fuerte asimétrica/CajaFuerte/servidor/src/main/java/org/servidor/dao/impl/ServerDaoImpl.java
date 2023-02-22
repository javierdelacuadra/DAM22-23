package org.servidor.dao.impl;

import jakarta.inject.Inject;
import org.servidor.config.Credentials;

import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ServerDaoImpl implements org.servidor.dao.ServerDao {

    private final Credentials credentials;

     @Inject
    public ServerDaoImpl(Credentials credentials) {
         this.credentials = credentials;
     }

        @Override
        public String getPublic() {
            X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(credentials.getPublicKey().getEncoded());
            return Base64.getUrlEncoder().encodeToString(x509Spec.getEncoded());
        }
}
