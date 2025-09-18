package org.caixaverso.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntidadeJaExisteExceptionMapper implements ExceptionMapper<EntidadeJaExisteException> {
    @Override
    public Response toResponse(EntidadeJaExisteException ex) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new ErroResponse("Já existe um registro com este ID.", ex.getMessage()))
                .build();
    }
}