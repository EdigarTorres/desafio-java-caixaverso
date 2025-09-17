package org.caixaverso.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.caixaverso.dto.ParcelaDetalhe;
import org.caixaverso.dto.SimulacaoResponse;
import org.caixaverso.model.ProdutoEmprestimo;
import org.caixaverso.util.Formatador;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    @Inject
    Formatador formatador;

    public SimulacaoResponse simular(ProdutoEmprestimo produto, double valor, int prazo) {

        double taxaMensalEfetiva = Math.pow(1 + produto.taxaJurosAnual / 100, 1.0 / 12) - 1;

        double parcelaFixa = valor * taxaMensalEfetiva / (1 - Math.pow(1 + taxaMensalEfetiva, -prazo));
        double saldoDevedor = valor;

        List<ParcelaDetalhe> detalhes = new ArrayList<>();

        for (int mes = 1; mes <= prazo; mes++) {
            double juros = saldoDevedor * taxaMensalEfetiva;
            double amortizacao = parcelaFixa - juros;
            saldoDevedor -= amortizacao;

            detalhes.add(new ParcelaDetalhe(
                    mes,
                    Formatador.formatarReais(amortizacao),
                    Formatador.formatarReais(juros),
                    Formatador.formatarReais(Math.max(saldoDevedor, 0))
            ));
        }

        SimulacaoResponse response = new SimulacaoResponse();
        response.nomeProduto = produto.nome;
        response.taxaJurosAnual = Formatador.formatarPercentual(produto.taxaJurosAnual / 100);
        response.taxaJurosMensal = Formatador.formatarPercentual(taxaMensalEfetiva);
        response.valorSolicitado = Formatador.formatarReais(valor);
        response.valorTotalComJuros = Formatador.formatarReais(parcelaFixa * prazo);
        response.valorParcelaMensal = Formatador.formatarReais(parcelaFixa);
        response.prazoMeses = prazo;
        response.parcelas = detalhes;

        return response;
    }


}



