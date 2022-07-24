package org.zhu4.tracker.application.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import org.zhu4.tracker.domain.Label
import org.zhu4.tracker.domain.Line
import org.zhu4.tracker.domain.security.User
import java.time.ZonedDateTime
import javax.validation.constraints.NotNull

data class LineDto(
    @field:NotNull
    val value: Long?,
    @field:NotNull
    val labels: List<String>?,
    @field:JsonIgnore
    val user: User? = null,
    val date: ZonedDateTime? = null
) {

    fun toLine(): Line {
        return Line(
            value = value!!,
            labels = labels!!.map { Label(value = it) },
            user = user!!
        )
    }
}
