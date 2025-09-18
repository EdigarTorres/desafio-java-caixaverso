package org.caixaverso.exception;

import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class EntidadeNaoEncontradaExceptionMapperTest {

    @Test
    void deveRetornarStatusNotFoundEBodyCorreto() {

        String mensagemDetalhe = "ID não localizado";
        EntidadeNaoEncontradaException ex = new EntidadeNaoEncontradaException(mensagemDetalhe);
        EntidadeNaoEncontradaExceptionMapper mapper = new EntidadeNaoEncontradaExceptionMapper();

        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity() instanceof ErroResponse);

        ErroResponse erro = (ErroResponse) response.getEntity();
        assertEquals("Entidade não encontrada", erro.erro);
        assertEquals(mensagemDetalhe, erro.detalhe);
    }
}