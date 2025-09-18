package org.caixaverso.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.caixaverso.exception.EntidadeJaExisteException;
import org.caixaverso.model.ProdutoEmprestimo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProdutoControllerTest {

    private ProdutoController controller;
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {

        entityManager = mock(EntityManager.class);
        controller = new ProdutoController();
        controller.entityManager = entityManager;
    }

    @Test
    public void testListarProdutos() {
        ProdutoEmprestimo p1 = new ProdutoEmprestimo();
        p1.nome = "Produto A";
        ProdutoEmprestimo p2 = new ProdutoEmprestimo();
        p2.nome = "Produto B";

        @SuppressWarnings("unchecked")
        TypedQuery<ProdutoEmprestimo> query = mock(TypedQuery.class);

        when(entityManager.createQuery("from ProdutoEmprestimo", ProdutoEmprestimo.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(p1, p2));

        List<ProdutoEmprestimo> result = controller.listar();

        assertEquals(2, result.size());
        assertEquals("Produto A", result.get(0).nome);
    }

    @Test
    public void testListarPorId() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 42L;
        produto.nome = "Produto Teste";
        when(entityManager.find(ProdutoEmprestimo.class, 42L)).thenReturn(produto);

        ProdutoEmprestimo result = controller.listarPorId(42L);

        assertEquals(42L, result.id);
        assertEquals("Produto Teste", result.nome);
    }

    @Test
    void testCadastrarProdutoNovo() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = null; // Produto novo, sem ID

        ProdutoEmprestimo result = controller.cadastrar(produto);

        verify(entityManager).persist(produto);
        assertSame(produto, result);
    }

    @Test
    void testCadastrarProdutoComIdNaoExistente() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 10L;

        when(entityManager.find(ProdutoEmprestimo.class, 10L)).thenReturn(null);

        ProdutoEmprestimo result = controller.cadastrar(produto);

        verify(entityManager).persist(produto);
        assertSame(produto, result);
    }

    @Test
    void testCadastrarProdutoComIdExistente() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 20L;

        when(entityManager.find(ProdutoEmprestimo.class, 20L)).thenReturn(new ProdutoEmprestimo());

        EntidadeJaExisteException ex = assertThrows(
                EntidadeJaExisteException.class,
                () -> controller.cadastrar(produto)
        );
        assertTrue(ex.getMessage().contains("Produto com ID 20 já existe."));
        verify(entityManager, never()).persist(any());
    }

    @Test
    public void testAtualizarProduto() {
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

        ProdutoEmprestimo result = controller.atualizar(1L, atualizado);

        assertEquals("Novo", result.nome);
        assertEquals(18.0, result.taxaJurosAnual);
        assertEquals(60, result.prazoMaximoMeses);
    }

    @Test
    public void testDeletarProduto() {
        ProdutoEmprestimo produto = new ProdutoEmprestimo();
        produto.id = 1L;

        when(entityManager.find(ProdutoEmprestimo.class, 1L)).thenReturn(produto);

        controller.deletar(1L);

        verify(entityManager).remove(produto);
    }
}
