package org.caixaverso.controller;

import org.caixaverso.dto.SimulacaoRequest;
import org.caixaverso.dto.SimulacaoResponse;
import org.caixaverso.model.ProdutoEmprestimo;
import org.caixaverso.service.SimulacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoControllerTest {

    private ProdutoController controller;
    private EntityManager entityManager;
    private SimulacaoService simulacaoService;

    @BeforeEach
    public void setup() {
        entityManager = mock(EntityManager.class);
        simulacaoService = mock(SimulacaoService.class);

        controller = new ProdutoController();
        controller.entityManager = entityManager;
        controller.simulacaoService = simulacaoService;
    }

    @Test
    public void testCreateProduto() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.nome = "Crédito Pessoal";
        produto.taxaJurosAnual = 18.0;
        produto.prazoMaximoMeses = 60;

        ProdutoEmprestimo result = controller.create(produto);

        verify(entityManager).persist(produto);
        assertEquals("Crédito Pessoal", result.nome);
    }

    @Test
    public void testSimularEmprestimo() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 1L;
        produto.nome = "Consignado";
        produto.taxaJurosAnual = 18.0;

        SimulacaoRequest request = new SimulacaoRequest();
        request.idProduto = 1L;
        request.valorSolicitado = 10000.0;
        request.prazoMeses = 24;

        SimulacaoResponse responseMock = new SimulacaoResponse();
        responseMock.nomeProduto = "Consignado";

        when(entityManager.find(ProdutoEmprestimo.class, 1L)).thenReturn(produto);
        when(simulacaoService.simular(produto, 10000.0, 24)).thenReturn(responseMock);

        SimulacaoResponse response = controller.simular(request);

        assertEquals("Consignado", response.nomeProduto);
        verify(simulacaoService).simular(produto, 10000.0, 24);
    }

    @Test
    public void testListProdutos() {
        ProdutoEmprestimo p1 = new ProdutoEmprestimo();
        p1.nome = "Produto A";
        ProdutoEmprestimo p2 = new ProdutoEmprestimo();
        p2.nome = "Produto B";

        TypedQuery<ProdutoEmprestimo> query = mock(TypedQuery.class);
        when(entityManager.createQuery("from ProdutoEmprestimo", ProdutoEmprestimo.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(p1, p2));

        List<ProdutoEmprestimo> result = controller.list();

        assertEquals(2, result.size());
        assertEquals("Produto A", result.get(0).nome);
    }

    @Test
    public void testUpdateProduto() {
        ProdutoEmprestimo existente = new ProdutoEmprestimo();
        existente.id = 1L;
        existente.nome = "Antigo";
        existente.taxaJurosAnual = 15.0;
        existente.prazoMaximoMeses = 48;

        ProdutoEmprestimo atualizado = new ProdutoEmprestimo();
        atualizado.nome = "Novo";
        atualizado.taxaJurosAnual = 18.0;
        atualizado.prazoMaximoMeses = 60;

        when(entityManager.find(ProdutoEmprestimo.class, 1L)).thenReturn(existente);

        ProdutoEmprestimo result = controller.update(1L, atualizado);

        assertEquals("Novo", result.nome);
        assertEquals(18.0, result.taxaJurosAnual);
        assertEquals(60, result.prazoMaximoMeses);
    }

    @Test
    public void testDeleteProduto() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 1L;

        when(entityManager.find(ProdutoEmprestimo.class, 1L)).thenReturn(produto);

        controller.delete(1L);

        verify(entityManager).remove(produto);
    }
}
