package org.caixaverso.exception;

public class ErroResponse {
    public String erro;
    public String detalhe;

    public ErroResponse(String erro, String detalhe) {
        this.erro = erro;
        this.detalhe = detalhe;
    }
}