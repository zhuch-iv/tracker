package org.zhu4.tracker.domain.security

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import org.hibernate.annotations.Immutable
import javax.persistence.*

@Entity
@Immutable
@Table(name = "groups")
@NamedQueries(value = [
    NamedQuery(
        name = "Group.findByValues",
        query = "FROM Group g WHERE g.value IN ?1 ORDER BY g.id"
    )
])
class Group(): PanacheEntity() {

    constructor(value: String) : this() {
        this.value = value
    }

    @Column(name = "value", nullable = false, unique = true)
    lateinit var value: String

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
