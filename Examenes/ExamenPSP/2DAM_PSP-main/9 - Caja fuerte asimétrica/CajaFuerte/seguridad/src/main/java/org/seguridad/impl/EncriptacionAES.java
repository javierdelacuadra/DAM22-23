package org.seguridad.impl;


import com.google.common.primitives.Bytes;
import lombok.extern.log4j.Log4j2;
import org.seguridad.Encriptacion;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Log4j2
public class EncriptacionAES implements Encriptacion {


    public static final String ERROR_WHILE_ENCRYPTING = "Error while encrypting: ";
    public static final String PBKDF_2_WITH_HMAC_SHA_256 = "PBKDF2WithHmacSHA256";
    public static final String AES = "AES";
    public static final String AES_GCM_NO_PADDING = "AES/GCM/noPADDING";
    public static final String ERROR_WHILE_DECRYPTING = "Error while decrypting: ";

    @Override
    public String encriptar(String strToEncrypt, String secret) {
        try {

            byte [] iv = new byte[12];
            byte []salt = new byte[16];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(iv);
            sr.nextBytes(salt);

            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

            SecretKeySpec secretKey = getSecretKeySpec(secret, salt);

            Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            return Base64.getUrlEncoder().encodeToString(Bytes.concat(iv,salt,
                    cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception e) {
            log.error(ERROR_WHILE_ENCRYPTING + e);
        }
        return null;
    }

    private SecretKeySpec getSecretKeySpec(String secret, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF_2_WITH_HMAC_SHA_256);
        // en el jdk8 esta limitado a 128 bits, desde el 9 puede ser de 256
        KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), AES);
    }

    @Override
    public String desencriptar(String strToDecrypt, String secret) {
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(strToDecrypt);

            byte[] iv = Arrays.copyOf(decoded, 12);
            byte []salt = Arrays.copyOfRange(decoded, 12,28);

            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

            SecretKeySpec secretKey = getSecretKeySpec(secret, salt);

            Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            return new String(cipher.doFinal(Arrays.copyOfRange(decoded, 28, decoded.length)), StandardCharsets.UTF_8);
        } catch (Exception e) {
           log.error(ERROR_WHILE_DECRYPTING + e);
        }
        return null;
    }
}

