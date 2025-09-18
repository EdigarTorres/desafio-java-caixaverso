package org.caixaverso.controller;

import jakarta.persistence.EntityManager;
import org.caixaverso.dto.ProdutoSimulacaoResponse;
import org.caixaverso.dto.SimulacaoRequest;
import org.caixaverso.dto.SimulacaoResponse;
import org.caixaverso.exception.EntidadeNaoEncontradaException;
import org.caixaverso.model.ProdutoEmprestimo;
import org.caixaverso.service.SimulacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulacaoControllerTest {

    private SimulacaoController controller;
    private EntityManager entityManager;
    private SimulacaoService simulacaoService;

    @BeforeEach
    void setUp() {
        controller = new SimulacaoController();
        entityManager = mock(EntityManager.class);
        simulacaoService = mock(SimulacaoService.class);

        controller.entityManager = entityManager;
        controller.simulacaoService = simulacaoService;
    }

    @Test
    void testSimularComSucesso() {
        SimulacaoRequest request = new SimulacaoRequest();
        request.idProduto = 1L;
        request.valorSolicitado = 1000.0;
        request.prazoMeses = 12;

        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        SimulacaoResponse simulacao = new SimulacaoResponse();

        when(entityManager.find(ProdutoEmprestimo.class, 1L)).thenReturn(produto);
        when(simulacaoService.simular(produto, 1000.0, 12)).thenReturn(simulacao);

        ProdutoSimulacaoResponse response = controller.simular(request);

        assertNotNull(response);
        assertEquals(produto, response.produto);
        assertEquals(simulacao, response.simulacao);
    }

    @Test
    void testSimularComRequestInvalido() {
        assertThrows(IllegalArgumentException.class, () -> controller.simular(null));

        SimulacaoRequest reqSemId = new SimulacaoRequest();
        reqSemId.idProduto = null;
        reqSemId.valorSolicitado = 1000.0;
        reqSemId.prazoMeses = 12;
        assertThrows(IllegalArgumentException.class, () -> controller.simular(reqSemId));

        SimulacaoRequest reqValorZero = new SimulacaoRequest();
        reqValorZero.idProduto = 1L;
        reqValorZero.valorSolicitado = 0.0;
        reqValorZero.prazoMeses = 12;
        assertThrows(IllegalArgumentException.class, () -> controller.simular(reqValorZero));

        SimulacaoRequest reqPrazoZero = new SimulacaoRequest();
        reqPrazoZero.idProduto = 1L;
        reqPrazoZero.valorSolicitado = 1000.0;
        reqPrazoZero.prazoMeses = 0;
        assertThrows(IllegalArgumentException.class, () -> controller.simular(reqPrazoZero));
    }

    @Test
    void testSimularProdutoNaoEncontrado() {
        SimulacaoRequest request = new SimulacaoRequest();
        request.idProduto = 2L;
        request.valorSolicitado = 500.0;
        request.prazoMeses = 6;

        when(entityManager.find(ProdutoEmprestimo.class, 2L)).thenReturn(null);

        EntidadeNaoEncontradaException ex = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> controller.simular(request)
        );
        assertTrue(ex.getMessage().contains("Produto com ID 2 não encontrado."));
    }
}