package org.zhu4.tracker.input

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
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Path("/tracker")
@ApplicationScoped
class TrackerResource(
    private val trackerService: TrackerService
) {

    @Inject
    internal lateinit var jwt: JsonWebToken

    @POST
    @RolesAllowed("User")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createLine(@Valid request: LineDto): Uni<Void> {
        return trackerService.createLine(request.copy(user = jwt.getCurrentUser()))
    }

    @GET
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLines(): Uni<List<LineDto>> {
        return trackerService.getLines(jwt.getCurrentUser())
    }
}
