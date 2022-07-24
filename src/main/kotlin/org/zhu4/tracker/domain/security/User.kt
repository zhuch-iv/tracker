package org.zhu4.tracker.domain.security

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
@NamedQueries(value = [
    NamedQuery(
        name = "User.findByIdFetchGroups",
        query = "FROM User u LEFT JOIN FETCH u.groups WHERE u.id = ?1 ORDER BY u.id"
    ),
    NamedQuery(
        name = "User.findByUsernameFetchGroups",
        query = "FROM User u LEFT JOIN FETCH u.groups WHERE u.username = ?1 ORDER BY u.id"
    )
])
class User(
    @Column(name = "username", nullable = false)
    var username: String? = null,
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "users_groups",
        joinColumns = [ JoinColumn(name = "user_id") ],
        inverseJoinColumns = [ JoinColumn(name = "group_id") ]
    )
    var groups: MutableSet<Group> = LinkedHashSet(),
    @Column(name = "password_hash", nullable = false)
    var passwordHash: String? = null,
    @Column(name = "email")
    var email: String? = null,
    @Column(name = "telegram")
    var telegram: String? = null,
): PanacheEntity() {

    constructor(id: Long): this() {
        this.id = id
    }

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    lateinit var updatedOn: ZonedDateTime
    @CreationTimestamp
    @Column(name = "created_on", nullable = false)
    lateinit var createdOn: ZonedDateTime
}
