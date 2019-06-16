package com.skuniv.cs.geonyeong.portal.service

import org.apache.commons.codec.binary.Base64
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Service
class CryptorService : InitializingBean {

    companion object {
        @JvmStatic
        private val MODE = "AES/CBC/PKCS5Padding"
        @JvmStatic
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private lateinit var ivSpec: IvParameterSpec
    private lateinit var secretKey: SecretKey

    private val ENCODE = Charset.defaultCharset()

    @Value("\${com.skuniv.cs.geonyeong.crypt.key}")
    private lateinit var key: String

    fun encryptBase64(source: String): String {
        val raw = encrypt(source.toByteArray())
        var encryptSource = try {
            String(Base64.encodeBase64(raw), ENCODE)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException()
        }
        return encryptSource
    }

    fun encrypt(org: ByteArray): ByteArray {
        try {
            val cipher = Cipher.getInstance(MODE)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
            return cipher.doFinal(org)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun decryptBase64(encryptedSource: String) : String {
        val raw = try {
            Base64.decodeBase64(encryptedSource.toByteArray(ENCODE));
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException()
        }
        return decrypt(raw)
    }

    fun decrypt(data: ByteArray) : String {
        try {
            val cipher = Cipher.getInstance(MODE)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
            val decryptedText = cipher.doFinal(data);
            return String(decryptedText, ENCODE)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    override fun afterPropertiesSet() {
        val keyData = key.substring(0, 16).toByteArray()
        secretKey = SecretKeySpec(keyData, "AES")
        ivSpec = IvParameterSpec(keyData)
    }
}