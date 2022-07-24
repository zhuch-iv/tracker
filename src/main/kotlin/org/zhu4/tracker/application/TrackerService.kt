package org.zhu4.tracker.application

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional
import io.smallrye.mutiny.Uni
import org.zhu4.tracker.application.dto.LineDto
import org.zhu4.tracker.domain.security.User
import org.zhu4.tracker.persistence.LineRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
@ReactiveTransactional
class TrackerService(
    private val lineRepository: LineRepository
) {

    fun createLine(request: LineDto): Uni<Void> =
        lineRepository.save(request.toLine())
            .replaceWithVoid()

    fun getLines(user: User): Uni<List<LineDto>> =
        lineRepository.findByUser(user)
            .map(Mappings::toLineDtoList)

}
