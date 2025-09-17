package org.caixaverso.service;

import org.caixaverso.dto.SimulacaoResponse;
import org.caixaverso.model.ProdutoEmprestimo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulacaoServiceTest {

    private final SimulacaoService service = new SimulacaoService();

    @Test
    void testSimulacaoComDadosValidos() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.nome = "Empréstimo Padrão";
        produto.taxaJurosAnual = 12.0;
        produto.prazoMaximoMeses = 24;

        double valor = 10000.0;
        int prazo = 12;

        SimulacaoResponse response = service.simular(produto, valor, prazo);

        assertEquals("Empréstimo Padrão", response.nomeProduto);
        assertEquals("12,00%", response.taxaJurosAnual);
        assertEquals("0,95%", response.taxaJurosMensal);
        assertEquals("R$ 10.000,00", normalizarEspacos(response.valorSolicitado));
        assertEquals("R$ 10.627,45", normalizarEspacos(response.valorTotalComJuros));
        assertEquals("R$ 885,62", normalizarEspacos(response.valorParcelaMensal));
        assertEquals(12, response.prazoMeses);
        assertEquals(12, response.parcelas.size());
    }

    @Test
    void testSimulacaoComValorZero() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.nome = "Zero";
        produto.taxaJurosAnual = 10.0;
        produto.prazoMaximoMeses = 10;

        SimulacaoResponse response = service.simular(produto, 0.0, 10);

        assertEquals("R$ 0,00", normalizarEspacos(response.valorSolicitado));
        assertEquals("R$ 0,00", normalizarEspacos(response.valorTotalComJuros));
        assertEquals("R$ 0,00", normalizarEspacos(response.valorParcelaMensal));
    }

    private String normalizarEspacos(String valor) {
        return valor.replace('\u00A0', ' ');
    }
}