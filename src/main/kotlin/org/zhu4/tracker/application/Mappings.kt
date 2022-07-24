package org.zhu4.tracker.application

import org.eclipse.microprofile.jwt.Claims
import org.eclipse.microprofile.jwt.JsonWebToken
import org.zhu4.tracker.application.dto.LineDto
import org.zhu4.tracker.domain.Label
import org.zhu4.tracker.domain.Line
import org.zhu4.tracker.domain.security.User

fun Line.toLineDto(): LineDto = LineDto(
    value = value,
    labels = labels.map(Label::value),
    date = createdOn
)

fun JsonWebToken.getCurrentUser(): User =
    User(id = this.getClaim<String>(Claims.upn).toLong())

object Mappings {
    fun toLineDtoList(lines: List<Line>): List<LineDto> = lines.map(Line::toLineDto)
}
