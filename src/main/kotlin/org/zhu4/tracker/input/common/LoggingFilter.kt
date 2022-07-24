package org.zhu4.tracker.input.common

import io.vertx.core.http.HttpServerRequest
import org.slf4j.LoggerFactory
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo
import javax.ws.rs.ext.Provider


@Provider
class LoggingFilter: ContainerRequestFilter {
    @Context
    lateinit var info: UriInfo

    @Context
    lateinit var request: HttpServerRequest

    override fun filter(context: ContainerRequestContext) {
        log.info("Request {} {} from IP {}", context.method, info.path, request.remoteAddress().toString())
    }

    companion object {
        private val log = LoggerFactory.getLogger(LoggingFilter::class.java)
    }
}
