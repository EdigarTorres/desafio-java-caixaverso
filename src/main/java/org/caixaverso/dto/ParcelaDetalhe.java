package org.caixaverso.dto;

public class ParcelaDetalhe {
    public int mes;
    public String amortizacao;
    public String juros;
    public String saldoDevedor;

    public ParcelaDetalhe(int mes, String amortizacao, String juros, String saldoDevedor) {
        this.mes = mes;
        this.amortizacao = amortizacao;
        this.juros = juros;
        this.saldoDevedor = saldoDevedor;
    }
}
