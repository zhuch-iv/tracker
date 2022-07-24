package org.zhu4.tracker.persistence.security

import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.smallrye.mutiny.Uni
import org.zhu4.tracker.domain.security.User
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository(
    private val groupRepository: GroupRepository
): PanacheRepository<User> {

    fun save(user: User): Uni<User> = groupRepository.save(user.groups)
        .flatMap {
            user.groups = it
            persist(user)
        }

    fun findByIdFetchGroups(id: Long): Uni<User> = find("#User.findByIdFetchGroups", id)
        .firstResult()

    fun findByUsernameFetchGroups(username: String): Uni<User> = find("#User.findByUsernameFetchGroups", username)
        .firstResult()
}
