package org.zhu4.tracker.domain

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.Immutable
import org.zhu4.tracker.domain.security.User
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Immutable
@NamedQueries(value = [
    NamedQuery(
        name = "Line.findByUser",
        query = "SELECT DISTINCT l FROM Line l LEFT JOIN FETCH l.labels WHERE l.user = ?1 ORDER BY l.id"
    )
])
@Table(name = "lines", indexes = [Index(name = "user_id_index", columnList = "user_id")])
class Line(
    @Column(name = "value", nullable = false)
    var value: Long = 0,
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "lines_labels",
        joinColumns = [ JoinColumn(name = "line_id") ],
        inverseJoinColumns = [ JoinColumn(name = "label_id") ]
    )
    var labels: List<Label> = emptyList(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
): PanacheEntity() {

    @CreationTimestamp
    @Column(name = "created_on", nullable = false)
    lateinit var createdOn: ZonedDateTime
}
