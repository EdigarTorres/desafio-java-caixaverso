package org.caixaverso.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntidadeJaExisteExceptionTest {

    @Test
    void deveConterMensagemCorreta() {
        String mensagem = "ID já cadastrado";
        EntidadeJaExisteException ex = new EntidadeJaExisteException(mensagem);

        assertEquals(mensagem, ex.getMessage());
    }
}