package org.zhu4.tracker

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.zhu4.tracker.application.security.PBKDF2passwordEncoder

class TokenSecuredTest {

    @Test
    fun testHelloEndpoint() {
        log.info(PBKDF2passwordEncoder.encodePassword("hash"))
        // 2048:8lIwp2WEhhtu8vz0ztlKNw==:kkU9TFGoTHMCcCH6K12UgMZXd4lyWkdTlQnXAVsbQSbNjLruTnsi1YEKRjpM0EmTVoSGXY9jGKoAaNbmc0CcQA==
    }

    companion object {
        private val log = LoggerFactory.getLogger(TokenSecuredTest::class.java)
    }
}
