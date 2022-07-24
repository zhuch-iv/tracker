package org.zhu4.tracker.persistence.security

import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.smallrye.mutiny.Uni
import org.slf4j.LoggerFactory
import org.zhu4.tracker.domain.security.Group
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GroupRepository: PanacheRepository<Group> {

    fun save(groups: Set<Group>): Uni<Set<Group>> {
        return find("#Group.findByValues", groups.map(Group::value))
            .list<Group>()
            .map { exists ->
                groups.filterTo(mutableSetOf(*exists.toTypedArray())) { label -> exists.none { it.value == label.value } }
            }
    }

    companion object {
        private val log = LoggerFactory.getLogger(GroupRepository::class.java)
    }
}
