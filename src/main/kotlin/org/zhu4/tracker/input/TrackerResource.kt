package org.zhu4.tracker.input

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.jwt.JsonWebToken
import org.zhu4.tracker.application.TrackerService
import org.zhu4.tracker.application.dto.LineDto
import org.zhu4.tracker.application.getCurrentUser
import javax.annotation.security.RolesAllowed
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType


@Path("/tracker")
@ApplicationScoped
class TrackerResource(
    private val trackerService: TrackerService
) {

    @Inject
    lateinit var jwt: JsonWebToken

    @POST
    @RolesAllowed("User")
    @ReactiveTransactional
    @Consumes(MediaType.APPLICATION_JSON)
    fun createLine(@Valid request: LineDto): Uni<Void> {
        return trackerService.createLine(request.copy(user = jwt.getCurrentUser()))
    }

    @GET
    @RolesAllowed("User")
    fun getLines(): Uni<List<LineDto>> {
        return trackerService.getLines(jwt.getCurrentUser())
    }
}
