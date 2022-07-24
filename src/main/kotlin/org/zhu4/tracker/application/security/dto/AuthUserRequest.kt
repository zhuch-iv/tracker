package org.zhu4.tracker.application.security.dto

import org.zhu4.tracker.domain.security.User
import javax.validation.constraints.NotBlank

data class AuthUserRequest(
    @field:NotBlank
    val username: String?,
    @field:NotBlank
    val password: String?,
    val user: User? = null
)
