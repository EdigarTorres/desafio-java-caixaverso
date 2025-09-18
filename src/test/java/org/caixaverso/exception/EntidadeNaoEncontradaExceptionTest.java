package org.caixaverso.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EntidadeNaoEncontradaExceptionTest {

    @Test
    void deveConterMensagemCorreta() {
        String mensagem = "Entidade não encontrada";
        EntidadeNaoEncontradaException ex = new EntidadeNaoEncontradaException(mensagem);

        assertEquals(mensagem, ex.getMessage());
    }
}