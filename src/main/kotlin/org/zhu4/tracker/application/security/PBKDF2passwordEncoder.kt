package org.zhu4.tracker.application.security

import org.slf4j.LoggerFactory
import java.security.SecureRandom
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


object PBKDF2passwordEncoder {

    fun encodePassword(password: String): String {
        val salt = getSalt()
        val spec = generateSpec(password.toCharArray(), salt)
        val hash = generateSecret(spec)
        return "${Algorithms.iterations}:${salt.toBase64()}:${hash.toBase64()}"
    }

    fun validate(originalPassword: String?, storedPassword: String?): Boolean {
        if (storedPassword == null || originalPassword == null) {
            return false
        }
        val parts = storedPassword.split(":")
        val iterations = parts[0].toInt()
        val salt = parts[1].fromBase64()
        val hash = parts[2].fromBase64()
        val spec = PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.size * 8)
        return validate(generateSecret(spec), hash)
    }

    private fun generateSpec(password: CharArray, salt: ByteArray): PBEKeySpec {
        return PBEKeySpec(password, salt, Algorithms.iterations, 64 * 8)
    }

    private fun generateSecret(spec: PBEKeySpec): ByteArray {
        return SecretKeyFactory.getInstance(Algorithms.hashing)
            .generateSecret(spec).encoded
    }

    private fun validate(testHash: ByteArray, hash: ByteArray): Boolean {
        var diff = hash.size xor testHash.size
        var i = 0
        while (i < hash.size && i < testHash.size) {
            diff = diff or (hash[i].toInt() xor testHash[i].toInt())
            i++
        }
        return diff == 0
    }

    private fun getSalt(): ByteArray {
        return generateRandomByteArray(16)
    }

    private fun generateRandomByteArray(size: Int): ByteArray {
        val random = SecureRandom.getInstance(Algorithms.random)
        val salt = ByteArray(size)
        random.nextBytes(salt)
        return salt
    }

    private fun ByteArray.toBase64(): String {
        return Base64.getEncoder().encodeToString(this)
    }

    private fun String.fromBase64(): ByteArray {
        return Base64.getDecoder().decode(this)
    }

    private object Algorithms {
        const val hashing = "PBKDF2WithHmacSHA512"
        const val random = "SHA1PRNG"
        const val iterations = 2048
    }

    private val log = LoggerFactory.getLogger(PBKDF2passwordEncoder::class.java)
}
