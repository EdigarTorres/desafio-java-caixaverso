package org.caixaverso.exception;

import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class EntidadeJaExisteExceptionMapperTest {

    @Test
    void deveRetornarStatusConflictEBodyCorreto() {

        String mensagemDetalhe = "ID duplicado";
        EntidadeJaExisteException ex = new EntidadeJaExisteException(mensagemDetalhe);
        EntidadeJaExisteExceptionMapper mapper = new EntidadeJaExisteExceptionMapper();

        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
        assertInstanceOf(ErroResponse.class, response.getEntity());

        ErroResponse erro = (ErroResponse) response.getEntity();
        assertEquals("Já existe um registro com este ID.", erro.erro);
        assertEquals(mensagemDetalhe, erro.detalhe);
    }
}