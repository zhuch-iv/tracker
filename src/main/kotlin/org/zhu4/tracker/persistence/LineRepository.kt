package org.zhu4.tracker.persistence

import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.smallrye.mutiny.Uni
import org.zhu4.tracker.domain.Line
import org.zhu4.tracker.domain.security.User
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class LineRepository(
    private val labelRepository: LabelRepository
): PanacheRepository<Line> {

    fun save(line: Line): Uni<Line> = labelRepository.save(line.labels)
        .flatMap {
            line.labels = it
            persist(line)
        }

    fun findByUser(user: User): Uni<List<Line>> =
        find("#Line.findByUser", user)
            .list()
}
