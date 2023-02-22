package security

interface Encriptacion {

    fun encriptar(strToEncrypt: String, secret: String): String?

    fun desencriptar(strToDecrypt: String, secret: String): String?
}