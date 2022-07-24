package org.zhu4.tracker.application.security

import io.smallrye.jwt.build.Jwt
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.context.ManagedExecutor
import org.slf4j.LoggerFactory
import org.zhu4.tracker.application.PasswordNotMatch
import org.zhu4.tracker.application.security.dto.AuthUserRequest
import org.zhu4.tracker.domain.security.Group
import org.zhu4.tracker.domain.security.Token
import org.zhu4.tracker.domain.security.User
import org.zhu4.tracker.persistence.security.UserRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.NotFoundException

@ApplicationScoped
class TokenService(
    private val userRepository: UserRepository
) {

    @Inject
    private lateinit var executor: ManagedExecutor

    @ConfigProperty(name = "smallrye.jwt.sign.duration")
    private var duration: Long = 0

    fun authUser(request: AuthUserRequest): Uni<Token> =
        userRepository.findByUsernameFetchGroups(request.username!!)
            .onItem().ifNull().failWith { NotFoundException("User not found") }
            .mapToToken(request)

    private fun Uni<User>.mapToToken(request: AuthUserRequest): Uni<Token> =
        this.emitOn(executor)
            .map { user -> validatePassAndGenerateToken(request.copy(user = user)) }

    private fun validatePassAndGenerateToken(request: AuthUserRequest): Token {
        if (!PBKDF2passwordEncoder.validate(request.password, request.user?.passwordHash)) {
            log.error("Password not match for user with id: ${request.user?.id}")
            throw PasswordNotMatch()
        }
        return generateToken(request.user!!, currentTimeSec())
    }

    private fun generateToken(user: User, currentTime: Long) =
        Token(
            Jwt.issuer("https://zhu4.org")
                .upn(user.id.toString())
                .groups(user.groups.mapTo(HashSet(), Group::value))
                .issuedAt(currentTime)
                .expiresAt(currentTime + duration)
                .sign()
        )

    fun currentTimeSec(): Long {
        return System.currentTimeMillis() / 1000
    }

    companion object {
        private val log = LoggerFactory.getLogger(TokenService::class.java)
    }
}
