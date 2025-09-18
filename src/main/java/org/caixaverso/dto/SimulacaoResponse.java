package org.caixaverso.dto;

import java.util.List;

public class SimulacaoResponse {
    public String valorSolicitado;
    public int prazoMeses;
    public String taxaJurosAnual;
    public String taxaJurosEfetivaMensal;
    public String valorTotalComJuros;
    public String valorParcelaMensal;

    public List<ParcelaDetalhe> memoriaCalculo;
}


