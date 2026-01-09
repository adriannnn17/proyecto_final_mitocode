package org.acme.infraestructure.exceptions.handlers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.acme.interfaces.resources.Result;

@Slf4j
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        log.error("Error generico", exception);
        String message = exception.getMessage();
        var result = Result.builder().data(message)
                .httpStatus(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        return Response.serverError().entity(result).build();
    }
}
