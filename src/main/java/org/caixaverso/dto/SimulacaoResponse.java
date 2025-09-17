package org.caixaverso.dto;

import java.util.List;

public class SimulacaoResponse {
    public String nomeProduto;
    public String taxaJurosAnual;
    public String taxaJurosMensal;
    public String valorSolicitado;
    public String valorTotalComJuros;
    public String valorParcelaMensal;
    public int prazoMeses;
    public List<ParcelaDetalhe> parcelas;
}


