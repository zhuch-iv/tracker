package org.zhu4.tracker.input

import io.smallrye.mutiny.Uni
import org.slf4j.LoggerFactory
import org.zhu4.tracker.application.security.dto.AuthUserRequest
import org.zhu4.tracker.application.security.dto.RegisterUserRequest
import org.zhu4.tracker.application.security.TokenService
import org.zhu4.tracker.application.security.UserService
import org.zhu4.tracker.domain.security.Token
import javax.enterprise.context.ApplicationScoped
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Path("/user")
@ApplicationScoped
class UserResource(
    private val tokenService: TokenService,
    private val userService: UserService
) {

    @POST
//    @RolesAllowed("Admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun register(@Valid request: RegisterUserRequest): Uni<Void> {
        return userService.register(request)
    }

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun token(@Valid request: AuthUserRequest): Uni<Token> {
        return tokenService.authUser(request)
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserResource::class.java)
    }
}
