package org.caixaverso.exception;

import org.junit.jupiter.api.Test;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class IllegalArgumentExceptionMapperTest {

    @Test
    void deveRetornarStatusBadRequestEBodyCorreto() {

        String mensagemDetalhe = "Parâmetro inválido";
        IllegalArgumentException ex = new IllegalArgumentException(mensagemDetalhe);
        IllegalArgumentExceptionMapper mapper = new IllegalArgumentExceptionMapper();

        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertInstanceOf(ErroResponse.class, response.getEntity());

        ErroResponse erro = (ErroResponse) response.getEntity();
        assertEquals("Requisição inválida", erro.erro);
        assertEquals(mensagemDetalhe, erro.detalhe);
    }
}