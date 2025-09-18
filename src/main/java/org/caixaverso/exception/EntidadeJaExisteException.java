package org.caixaverso.exception;

public class EntidadeJaExisteException extends RuntimeException {
    public EntidadeJaExisteException(String mensagem) {
        super(mensagem);
    }
}