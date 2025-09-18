package org.caixaverso.dto;

import org.caixaverso.model.ProdutoEmprestimo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoSimulacaoResponseTest {

    @Test
    void testEqualsAndHashCode() {
        ProdutoEmprestimo produto1 = mock(ProdutoEmprestimo.class);
        ProdutoEmprestimo produto2 = mock(ProdutoEmprestimo.class);
        SimulacaoResponse simulacao1 = mock(SimulacaoResponse.class);
        SimulacaoResponse simulacao2 = mock(SimulacaoResponse.class);

        ProdutoSimulacaoResponse resp1 = new ProdutoSimulacaoResponse(produto1, simulacao1);
        ProdutoSimulacaoResponse resp2 = new ProdutoSimulacaoResponse(produto1, simulacao1);
        ProdutoSimulacaoResponse resp3 = new ProdutoSimulacaoResponse(produto2, simulacao2);

        assertEquals(resp1, resp2);
        assertEquals(resp1.hashCode(), resp2.hashCode());
        assertNotEquals(resp3, resp1);
        assertNotEquals(resp3.hashCode(), resp1.hashCode());
        assertNotEquals(null, resp1);
        assertNotEquals(new Object(), resp1);
    }

    @Test
    void testConstructor() {
        ProdutoEmprestimo produto = mock(ProdutoEmprestimo.class);
        SimulacaoResponse simulacao = mock(SimulacaoResponse.class);

        ProdutoSimulacaoResponse response = new ProdutoSimulacaoResponse(produto, simulacao);

        assertSame(produto, response.produto);
        assertSame(simulacao, response.simulacao);
    }

    @Test
    void testEqualsBranches() {
        ProdutoEmprestimo produto1 = mock(ProdutoEmprestimo.class);
        ProdutoEmprestimo produto2 = mock(ProdutoEmprestimo.class);
        SimulacaoResponse simulacao1 = mock(SimulacaoResponse.class);
        SimulacaoResponse simulacao2 = mock(SimulacaoResponse.class);

        ProdutoSimulacaoResponse base = new ProdutoSimulacaoResponse(produto1, simulacao1);
        ProdutoSimulacaoResponse diffProduto = new ProdutoSimulacaoResponse(produto2, simulacao1);
        ProdutoSimulacaoResponse diffSimulacao = new ProdutoSimulacaoResponse(produto1, simulacao2);

        assertEquals(base, base);
        assertNotEquals(null, base);
        assertNotEquals(base, diffProduto);
        assertNotEquals(base, diffSimulacao);
    }

    @Test
    void testEqualsWithNullObject() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        SimulacaoResponse simulacao = new SimulacaoResponse();

        ProdutoSimulacaoResponse response = new ProdutoSimulacaoResponse(produto, simulacao);

        assertNotEquals(null, response, "equals deve retornar false quando comparado com null");
    }

    @Test
    void testEqualsWithDifferentClass() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        SimulacaoResponse simulacao = new SimulacaoResponse();

        ProdutoSimulacaoResponse response = new ProdutoSimulacaoResponse(produto, simulacao);

        String diferente = "Objeto de classe diferente";

        assertNotEquals("equals deve retornar false quando comparado com objeto de classe diferente", response, diferente);
    }
}