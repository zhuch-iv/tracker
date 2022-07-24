package org.zhu4.tracker.persistence

import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.smallrye.mutiny.Uni
import org.zhu4.tracker.domain.Label
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class LabelRepository : PanacheRepository<Label> {

    fun save(labels: Set<Label>): Uni<MutableSet<Label>> {
        return find("#Label.findByValues", labels.map(Label::value))
            .list<Label>()
            .map { exists ->
                labels.filterTo(mutableSetOf(*exists.toTypedArray())) { label -> exists.none { it.value == label.value } }
            }
    }
}