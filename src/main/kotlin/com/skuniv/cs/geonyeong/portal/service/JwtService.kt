package com.skuniv.cs.geonyeong.portal.service

import com.skuniv.cs.geonyeong.portal.repository.ProfessorRepository
import com.skuniv.cs.geonyeong.portal.repository.StudentRepository
import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

@Service
class JwtService(
        val professorRepository: ProfessorRepository,
        val studentRepository: StudentRepository
) {
    companion object {
        @JvmStatic
        val log = LoggerFactory.getLogger(this::class.java)
    }

    @Value("\${com.skuniv.cs.geonyeong.jwt.secretKey}")
    private lateinit var secretKey: String

    private val EXPIRE_TIME = 1000 * 60 * 60 * 1L
    private val signatureAlgorithm = SignatureAlgorithm.HS256
    private val ID_FIELD = "id"
    private val PASSWORD_FIELD = "password"
    private val headerMap = object : HashMap<String, Any>() {
        init {
            put("typ", "JWT")
            put("alg", "HS256")
        }
    }

    fun makeJwt(id: String, password: String): String {
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey)
        val signingKey = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)

        val map = object : HashMap<String, Any>() {
            init {
                put(ID_FIELD, id)
                put(PASSWORD_FIELD, password)
            }
        }
        val expireTime = Date()
        expireTime.time = expireTime.time + EXPIRE_TIME
        val builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expireTime)
                .signWith(signingKey, signatureAlgorithm)
        return builder.compact()
    }

    fun checkProfessor(jwt: String): Boolean {
        try {
            val claims = makeClaims(jwt)
            val id = claims.get(ID_FIELD).toString()
            val password = claims.get(PASSWORD_FIELD).toString()
            val findProfessor = professorRepository.findByIdAndPassword(id, password)
            return if (findProfessor != null) true else false
        } catch (exception: ExpiredJwtException) {
            throw exception
        } catch (exception: JwtException) {
            log.info("토큰 변조")
            return false
        }
    }

    fun checkStudent(jwt: String): Boolean {
        try {
            val claims = makeClaims(jwt)
            return true
        } catch (exception: ExpiredJwtException) {
            throw exception
        } catch (exception: JwtException) {
            log.info("토큰 변조")
            return false
        }

    }

    private fun makeClaims(jwt: String): Claims {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(jwt).body
    }

    fun getJwtId(jwt: String) : String {
        val claims = makeClaims(jwt)
        return claims.get(ID_FIELD) as String
    }
}

