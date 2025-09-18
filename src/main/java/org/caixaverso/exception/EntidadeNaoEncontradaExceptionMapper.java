package org.caixaverso.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntidadeNaoEncontradaExceptionMapper implements ExceptionMapper<EntidadeNaoEncontradaException> {
    @Override
    public Response toResponse(EntidadeNaoEncontradaException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErroResponse("Entidade não encontrada", ex.getMessage()))
                .build();
    }
}