package org.seguridad.impl;


import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.seguridad.EncriptacionAsimetrica;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Firma;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Log4j2
public class EncriptAsim implements EncriptacionAsimetrica {

    private static final String RSA = "RSA";
    private static final String CN = "CN=";
    private static final String X_509 = "X.509";
    private static final String ERROR_AL_OBTENER_EL_CERTIFICADO = "Error al obtener el certificado";
    private static final String ERROR_AL_GENERAR_EL_CERTIFICADO = "Error al generar el certificado";
    private static final String ERROR_AL_FIRMAR_EL_CERTIFICADO = "Error al firmar el certificado";
    private static final String SHA_256_WITH_RSAENCRYPTION = "SHA256WithRSAEncryption";
    private static final String ERROR_AL_GENERAR_EL_PAR_DE_CLAVES_ASIMETRICAS = "Error al generar el par de claves asimétricas";
    private static final String PKCS_12 = "PKCS12";
    private static final String PUBLICA = "publica";
    private static final String PRIVADA = "privada";
    private static final String ERROR_AL_OBTENER_EL_KEY_STORE = "Error al obtener el KeyStore";
    private static final String ERROR_AL_OBTENER_EL_CERTIFICADO_DEL_KEY_STORE = "Error al obtener el certificado del KeyStore";
    private static final String ERROR_AL_OBTENER_LA_CLAVE_PRIVADA_DEL_KEY_STORE = "Error al obtener la clave privada del KeyStore";
    private static final String ERROR_AL_LEER_LA_PUBLIC_KEY = "Error al leer la public key";
    private static final String SHA_256_WITH_RSA = "SHA256WithRSA";

    public EncriptAsim() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null)
            Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Método que genera un par de claves asimétricas
     *
     * @return Par de claves asimétricas
     */
    @Override
    public Either<String, KeyPair> generarClavesAsimetricas() {
        KeyPairGenerator generadorRSA2048; // Hace uso del provider BC
        try {
            generadorRSA2048 = KeyPairGenerator.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return Either.left(ERROR_AL_GENERAR_EL_PAR_DE_CLAVES_ASIMETRICAS);
        }
        generadorRSA2048.initialize(2048);
        return Either.right(generadorRSA2048.generateKeyPair()); // Genera las claves (se pueden acceder con un getPrivate o getPublic)
    }

    /**
     * Método que genera un certificado y lo devuelve codificado en Base64
     *
     * @param ownerPublicKey   Clave pública del propietario del certificado
     * @param nameOwner        Nombre del propietario del certificado
     * @param issuerPrivateKey Clave privada del emisor del certificado
     * @param nameIssuer       Nombre del emisor del certificado
     * @return Either con error o certificado codificado en Base64
     */
    @Override
    public Either<String, String> certificar(PublicKey ownerPublicKey, String nameOwner, PrivateKey issuerPrivateKey, String nameIssuer) {
        //Se crea un certificado autofirmado con la clave pública
        X500Name owner = new X500Name(CN + nameOwner);
        X500Name issuer = new X500Name(CN + nameIssuer);
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer, //issuer
                BigInteger.valueOf(1), //serial number
                Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)), //not valid before
                Date.from(LocalDate.now().plus(1, ChronoUnit.YEARS).atStartOfDay().toInstant(ZoneOffset.UTC)), //not valid after
                owner, //subject
                ownerPublicKey //public key
        );

        //Se firma el certificado con la clave privada
        ContentSigner signer;
        try {
            signer = new JcaContentSignerBuilder(SHA_256_WITH_RSAENCRYPTION).build(issuerPrivateKey);
        } catch (OperatorCreationException e) {
            log.error(e.getMessage());
            return Either.left(ERROR_AL_FIRMAR_EL_CERTIFICADO);
        }

        //Se obtiene el certificado
        X509Certificate certificate;
        byte[] encodedCert;
        try {
            certificate = new JcaX509CertificateConverter().getCertificate(certBuilder.build(signer));
            encodedCert = certificate.getEncoded();
        } catch (CertificateException e) {
            log.error(ERROR_AL_GENERAR_EL_CERTIFICADO, e);
            return Either.left(ERROR_AL_GENERAR_EL_CERTIFICADO);
        }
        return Either.right(Base64.getUrlEncoder().encodeToString(encodedCert)); //Se codifica en base64 el certificado creado
    }

    /**
     * Método que obtiene un certificado a partir de un String codificado en Base64
     *
     * @param certificadoBase64 Certificado codificado en Base64
     * @return Either con error o certificado
     */
    @Override
    public Either<String, X509Certificate> getCertificado(String certificadoBase64) {

        byte[] certificadoDecodificado = Base64.getUrlDecoder().decode(certificadoBase64);

        try {
            //lo convertimos a certificado
            CertificateFactory cf = CertificateFactory.getInstance(X_509);
            return Either.right((X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificadoDecodificado)));
        } catch (CertificateException e) {
            log.error(ERROR_AL_OBTENER_EL_CERTIFICADO, e);
            return Either.left(ERROR_AL_OBTENER_EL_CERTIFICADO);
        }
    }

    /**
     * Método que obtiene la clave pública de un certificado codificado en Base64
     *
     * @param certificate Certificado codificado en Base64
     * @return Either con error o clave pública
     */
    @Override
    public Either<String, PublicKey> getPublicKeyFromCertificateEncoded(String certificate) {
        return getCertificado(certificate).map(X509Certificate::getPublicKey)
                .mapLeft(error -> {
                        log.error(error);
                        return error;
                });
    }

    /**
     * Método que obtiene un KeyStore a partir de un fichero
     *
     * @param pathKeyStore Ruta del KeyStore
     * @param password     Contraseña del KeyStore
     * @return Either con el error o el KeyStore
     */
    @Override
    public Either<String, KeyStore> getKeyStore(String pathKeyStore, String password) {
        KeyStore keyStore;
        try (FileInputStream fileInputStream = new FileInputStream(pathKeyStore)) {
            keyStore = KeyStore.getInstance(PKCS_12);
            keyStore.load(fileInputStream, password.toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            log.error(ERROR_AL_OBTENER_EL_KEY_STORE, e);
            log.info(Paths.get(pathKeyStore).toAbsolutePath().toString());
            return Either.left("Usuario o contraseña incorrectos");
        }
        return Either.right(keyStore);
    }

    /**
     * Método que obtiene el certificado de un KeyStore
     *
     * @param keyStore KeyStore
     * @return Either con el error o el certificado
     */
    @Override
    public Either<String, X509Certificate> getCertFromKeyStore(KeyStore keyStore) {
        try {
            return Either.right((X509Certificate) keyStore.getCertificate(PUBLICA));
        } catch (KeyStoreException e) {
            log.error(ERROR_AL_OBTENER_EL_CERTIFICADO_DEL_KEY_STORE, e);
            return Either.left(ERROR_AL_OBTENER_EL_CERTIFICADO_DEL_KEY_STORE);
        }
    }

    /**
     * Método que obtiene la clave privada de un KeyStore
     *
     * @param keyStore KeyStore
     * @param password Contraseña del KeyStore
     * @return Either con error o clave privada
     */
    @Override
    public Either<String, PrivateKey> getPrivateKeyFromKeyStore(KeyStore keyStore, String password) {
        try {
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(PRIVADA, pt);
            return Either.right(privateKeyEntry.getPrivateKey());
        } catch (UnrecoverableEntryException | KeyStoreException | NoSuchAlgorithmException e) {
            log.error(ERROR_AL_OBTENER_LA_CLAVE_PRIVADA_DEL_KEY_STORE, e);
            return Either.left(ERROR_AL_OBTENER_LA_CLAVE_PRIVADA_DEL_KEY_STORE);
        }
    }

    /**
     * Método que obtiene la clave pública de un KeyStore
     *
     * @param publicKey String codificado en Base64 de la clave pública
     * @return Either con error o clave pública
     */
    @Override
    public Either<String, PublicKey> getPublicKeyFromStringBase64(String publicKey) {
        try {
            byte[] publicBytes = Base64.getUrlDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return Either.right(keyFactory.generatePublic(keySpec));
        } catch (Exception e) {
            log.error(ERROR_AL_LEER_LA_PUBLIC_KEY, e);
            return Either.left(ERROR_AL_LEER_LA_PUBLIC_KEY);
        }
    }

    /**
     * Método que codifica en Base64 una clave pública
     *
     * @param aPublic Clave pública a codificar
     * @return String con la clave pública codificada en Base64
     */
    @Override
    public String getStringOfPublicKey(PublicKey aPublic) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(aPublic.getEncoded());
        return Base64.getUrlEncoder().encodeToString(x509EncodedKeySpec.getEncoded());
    }

    /**
     * @param strToEncrypt String a encriptar (En caso del registro debería ser el String con el que ciframos simétricamente la PK del usuario)
     * @param aPublic      PK con la que se encriptará (En caso del registro debería ser la PK del servidor)
     * @return String encriptado en Base64
     */
    @Override
    public String encriptarConClavePublica(String strToEncrypt, PublicKey aPublic) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cifrador = getCipher();
        cifrador.init(Cipher.ENCRYPT_MODE, aPublic);
        return Base64.getUrlEncoder().encodeToString(cifrador.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));

    }

    /**
     * @param strToDecrypt String encriptado en Base64 a cifrado con PK a desencriptar
     * @param privateKey   PK con la que se desencriptará
     * @return String desencriptado (en caso del registro debería ser la clave con la que se encriptó la pk del usuario)
     */
    @Override
    public String desencriptarBase64ConClavePrivada(String strToDecrypt, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cifrador = getCipher();
        cifrador.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cifrador.doFinal(Base64.getUrlDecoder().decode(strToDecrypt)));
    }

    private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance(RSA);
    }

    /**
     *
     * @param secretKey Contraseña del KeyStore y de la clave privada que aloja el mismo
     * @param aPrivate Clave privada a guardar en el KeyStore
     * @param certificateBase64 Certificado del usuario en base64, se decodificará aquí
     * @param path Ruta donde se guardará el KeyStore
     */
    @Override
    public void createKeyStore(char[] secretKey, PrivateKey aPrivate, String certificateBase64, Path path) {
        //Se crea un KeyStore
        KeyStore ks;
        try {
            ks = generateKeyStoreInstance();
            // Se obtiene el certificado del usuario
            Either<String, X509Certificate> certEither = getCertificado(certificateBase64);

            //Se guarda el certificado en el KeyStore
            ks.setCertificateEntry(AplicationConstants.PUBLICA, certEither.get());
            //Se guarda la clave privada en el KeyStore con la misma secretKey que el KeyStore
            ks.setKeyEntry(AplicationConstants.PRIVADA, aPrivate, secretKey, new Certificate[]{certEither.get()});
        } catch (KeyStoreException e) {
            throw new RuntimeException("No se ha podido guardar el certificado y la clave privada en el KeyStore");
        }

        // Se guarda en el directorio del proyecto en el cliente problema en el servidor web no se puede guardar
        // en el directorio del proyecto porque se pierde al reiniciar el servidor
        // se ha de guardar en un directorio del container en el que el servidor web tenga permisos
        try (OutputStream fos = new FileOutputStream(path.toFile())) {
            //Se guarda el KeyStore en el fichero
            ks.store(fos, secretKey);
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new RuntimeException("No se ha podido guardar el KeyStore en el fichero");
        }
    }

    private KeyStore generateKeyStoreInstance() {
        KeyStore ks;
        try {
            ks = KeyStore.getInstance(PKCS_12);
            ks.load(null, null);
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("No se ha podido crear el KeyStore");
        }
        return ks;
    }

    /**
     *
     * @param privateKey Clave privada con la que se firmará
     * @param textoAFirmar Texto a firmar, si es null se firmará un texto aleatorio
     * @return Objeto firma que contiene el texto que se ha firmado y la firma codificada en Base64
     */
    @Override
    public Firma firmarConPrivateKey(PrivateKey privateKey, String textoAFirmar) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SHA_256_WITH_RSA);
        signature.initSign(privateKey);
        if (textoAFirmar == null) {
            SecureRandom sr = new SecureRandom();
            byte[] bytes = new byte[20];
            sr.nextBytes(bytes);
            textoAFirmar = Base64.getUrlEncoder().encodeToString(bytes);
        }
        signature.update(textoAFirmar.getBytes());
        byte[] firma = signature.sign();
        return new Firma(textoAFirmar, Base64.getUrlEncoder().encodeToString(firma));
    }

    /**
     *
     * @param publicKey Clave pública con la que se verificará la firma
     * @param firma Objeto firma que contiene el texto que se ha firmado y la firma codificada en Base64
     * @return true si la firma es correcta, false en caso contrario
     */
    @Override
    public boolean verificarfirma(PublicKey publicKey, Firma firma) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SHA_256_WITH_RSA);
        signature.initVerify(publicKey);
        signature.update(firma.getTextoFirmado().getBytes());
        return signature.verify(Base64.getUrlDecoder().decode(firma.getSrFirmado()));
    }

    /**
     *
     * @param keyStore KeyStore del que se obtendrá la clave pública
     * @return Clave pública del certificado alamacenado en el KeyStore
     */
    @Override
    public PublicKey getPublicKeyFromKeyStore(KeyStore keyStore) {
        try {
            return keyStore.getCertificate(AplicationConstants.PUBLICA).getPublicKey();
        } catch (KeyStoreException e) {
            throw new RuntimeException("No se ha podido obtener la clave pública del KeyStore");
        }
    }
}
