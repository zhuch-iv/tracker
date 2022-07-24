package org.zhu4.tracker.application.security.dto

import org.zhu4.tracker.application.security.PBKDF2passwordEncoder
import org.zhu4.tracker.domain.security.Group
import org.zhu4.tracker.domain.security.User
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RegisterUserRequest(
    @field:NotBlank
    val username: String?,
    @field:NotBlank
    val password: String?,
    @field:NotNull
    var groups: Set<String>?,
    var email: String? = null,
    var telegram: String? = null,
) {

    fun toUser(): User {
        return User(
            username = username!!,
            passwordHash = PBKDF2passwordEncoder.encodePassword(password!!),
            groups = groups!!.mapTo(HashSet()) { Group(it) }
        )
    }
}
