package security.impl

import com.google.common.primitives.Bytes
import security.Encriptacion
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object EncriptacionAES : Encriptacion {

    const val PBKDF_2_WITH_HMAC_SHA_256 = "PBKDF2WithHmacSHA256"
    const val AES = "AES"
    const val AES_GCM_NO_PADDING = "AES/GCM/noPADDING"
    override fun encriptar(strToEncrypt: String, secret: String): String? {
        try {
            val iv = ByteArray(12)
            val salt = ByteArray(16)
            val sr = SecureRandom()
            sr.nextBytes(iv)
            sr.nextBytes(salt)
            val parameterSpec = GCMParameterSpec(128, iv)
            val factory = SecretKeyFactory.getInstance(PBKDF_2_WITH_HMAC_SHA_256)
            // en el jdk8 esta limitado a 128 bits, desde el 9 puede ser de 256
            val spec: KeySpec = PBEKeySpec(secret.toCharArray(), salt, 65536, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.encoded, AES)
            val cipher = Cipher.getInstance(AES_GCM_NO_PADDING)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec)
            return Base64.getUrlEncoder().encodeToString(
                Bytes.concat(
                    iv, salt,
                    cipher.doFinal(strToEncrypt.toByteArray(StandardCharsets.UTF_8))
                )
            )
        } catch (e: Exception) {
            return null
        }
    }

    override fun desencriptar(strToDecrypt: String, secret: String): String? {
        return try {
            val decoded = Base64.getUrlDecoder().decode(strToDecrypt)
            val iv = Arrays.copyOf(decoded, 12)
            val salt = Arrays.copyOfRange(decoded, 12, 28)
            val parameterSpec = GCMParameterSpec(128, iv)
            val factory = SecretKeyFactory.getInstance(PBKDF_2_WITH_HMAC_SHA_256)
            val spec: KeySpec = PBEKeySpec(secret.toCharArray(), salt, 65536, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.encoded, AES)
            val cipher = Cipher.getInstance(AES_GCM_NO_PADDING)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec)
            String(cipher.doFinal(Arrays.copyOfRange(decoded, 28, decoded.size)), StandardCharsets.UTF_8)
        } catch (e: Exception) {
            null
        }
    }

}