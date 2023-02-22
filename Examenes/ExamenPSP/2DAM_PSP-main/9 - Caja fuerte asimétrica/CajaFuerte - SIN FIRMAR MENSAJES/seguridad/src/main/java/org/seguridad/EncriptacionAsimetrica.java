package org.seguridad;

import io.vavr.control.Either;
import org.utils.domain.modelo.Firma;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.X509Certificate;

public interface EncriptacionAsimetrica {
    Either<String, KeyPair> generarClavesAsimetricas();

    Either<String, String> certificar(PublicKey ownerPublicKey, String nameOwner, PrivateKey issuerPrivateKey, String nameIssuer);

    Either<String, X509Certificate> getCertificado(String certificadoBase64);

    Either<String, PublicKey> getPublicKeyFromCertificateEncoded(String certificate);

    Either<String, KeyStore> getKeyStore(String pathKeyStore, String password);

    Either<String, X509Certificate> getCertFromKeyStore(KeyStore keyStore);

    Either<String, PrivateKey> getPrivateKeyFromKeyStore(KeyStore keyStore, String password);

    Either<String, PublicKey> getPublicKeyFromStringBase64(String publicKey);

    String getStringOfPublicKey(PublicKey aPublic);

    String encriptarConClavePublica(String strToEncrypt, PublicKey aPublic) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    String desencriptarBase64ConClavePrivada(String strToDecrypt, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    void createKeyStore(char[] secretKey, PrivateKey aPrivate, String certificateBase64, Path path);

    Firma firmarConPrivateKey(PrivateKey privateKey, String textoAFirmar) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;

    boolean verificarfirma(PublicKey publicKey, Firma firma) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;

    PublicKey getPublicKeyFromKeyStore(KeyStore keyStore);
}
