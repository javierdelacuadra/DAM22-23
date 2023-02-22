package org.servidor.config;


import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.seguridad.EncriptacionAsimetrica;
import org.utils.common.AplicationConstants;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Log4j2
@Singleton
public class Credentials {


    //KeyStore FILE
    private PublicKey publicKey;
    private PrivateKey privateKey;

    private final EncriptacionAsimetrica encriptacionAsimetrica;

    @Inject
    public Credentials(EncriptacionAsimetrica ea, Configuration config) {
        this.encriptacionAsimetrica = ea;
        try (InputStream f = getClass().getResourceAsStream(config.getPathKeystore())){
            KeyStore ks = KeyStore.getInstance(AplicationConstants.PKCS_12);
            ks.load(f, config.getPasswordKeystore().toCharArray());
            encriptacionAsimetrica.getPrivateKeyFromKeyStore(ks, config.getPasswordKeystore()).peek(privateK -> this.privateKey = privateK).peekLeft(log::error);
            encriptacionAsimetrica.getCertFromKeyStore(ks).peek(x509Certificate -> this.publicKey = x509Certificate.getPublicKey()).peekLeft(log::error);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }

    }
}
