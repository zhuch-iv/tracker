package org.zhu4.tracker.domain

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Immutable
import org.zhu4.tracker.domain.security.Group
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Immutable
@NamedQueries(value = [
    NamedQuery(
        name = "Label.findByValues",
        query = "FROM Label l WHERE l.value IN ?1 ORDER BY l.id"
    )
])
@Table(name = "labels", indexes = [Index(name = "value_index", columnList = "value")])
class Label(): PanacheEntity() {

    constructor(value: String) : this() {
        this.value = value
    }

    @Column(name = "value", nullable = false, unique = true)
    lateinit var value: String

    @CreationTimestamp
    @Column(name = "created_on", nullable = false)
    lateinit var createdOn: ZonedDateTime

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Group
        if (value != other.value) return false
        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
