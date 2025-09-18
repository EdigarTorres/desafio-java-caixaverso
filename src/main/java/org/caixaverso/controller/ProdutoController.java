package org.caixaverso.controller;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.caixaverso.exception.EntidadeJaExisteException;
import org.caixaverso.model.ProdutoEmprestimo;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Transactional
@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoController {

    @Inject
    EntityManager entityManager;

    @GET
    @Operation(
            summary = "Lista todos os produtos de empréstimo",
            description = "Retorna uma lista de todos os produtos de empréstimo disponíveis no sistema."
    )
    public List<ProdutoEmprestimo> listar() {
        return entityManager.createQuery("from ProdutoEmprestimo", ProdutoEmprestimo.class).getResultList();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Busca um produto de empréstimo por ID",
            description = "Recebe o ID do produto na URL e retorna os detalhes do produto correspondente."
    )
    public ProdutoEmprestimo listarPorId(@PathParam("id") Long id) {
        return entityManager.find(ProdutoEmprestimo.class, id);
    }

    @POST
    @Operation(
            summary = "Cadastra um novo produto de empréstimo",
            description = "Recebe os detalhes do produto de empréstimo no corpo da requisição e o salva no banco de dados."
    )
    public ProdutoEmprestimo cadastrar(ProdutoEmprestimo produto) {
        if (produto.id != null && entityManager.find(ProdutoEmprestimo.class, produto.id) != null) {
            throw new EntidadeJaExisteException("Produto com ID " + produto.id + " já existe.");
        }
        entityManager.persist(produto);
        return produto;
    }

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Atualiza um produto de empréstimo",
            description = "Recebe o ID do produto na URL e os novos detalhes no corpo da requisição para atualizar o produto existente."
    )
    public ProdutoEmprestimo atualizar(@PathParam("id") Long id, ProdutoEmprestimo produto) {
        ProdutoEmprestimo existing = entityManager.find(ProdutoEmprestimo.class, id);
        existing.nome = produto.nome;
        existing.taxaJurosAnual = produto.taxaJurosAnual;
        existing.prazoMaximoMeses = produto.prazoMaximoMeses;
        return existing;
    }

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Deleta um produto de empréstimo",
            description = "Recebe o ID do produto na URL e o remove do banco de dados."
    )
    public void deletar(@PathParam("id") Long id) {
        entityManager.remove(entityManager.find(ProdutoEmprestimo.class, id));
    }
}
