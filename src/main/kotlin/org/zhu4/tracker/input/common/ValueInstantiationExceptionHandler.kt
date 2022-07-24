package org.zhu4.tracker.input.common

import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class ValueInstantiationExceptionHandler: ExceptionMapper<ValueInstantiationException> {

    override fun toResponse(exception: ValueInstantiationException): Response {
        return Response.status(Response.Status.BAD_REQUEST).build()
    }
}
