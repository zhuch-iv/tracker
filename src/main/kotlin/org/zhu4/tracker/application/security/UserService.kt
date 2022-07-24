package org.zhu4.tracker.application.security

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.context.ManagedExecutor
import org.zhu4.tracker.application.security.dto.RegisterUserRequest
import org.zhu4.tracker.persistence.security.UserRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
@ReactiveTransactional
class UserService(
    private val userRepository: UserRepository
) {

    @Inject
    internal lateinit var executor: ManagedExecutor

    fun register(request: RegisterUserRequest): Uni<Void> =
        Uni.createFrom().item(request)
            .emitOn(executor)
            .map(RegisterUserRequest::toUser)
            .flatMap { userRepository.save(it) }
            .replaceWithVoid()
}
