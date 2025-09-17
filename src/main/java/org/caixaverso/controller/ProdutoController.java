package org.caixaverso.controller;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.caixaverso.dto.SimulacaoRequest;
import org.caixaverso.dto.SimulacaoResponse;
import org.caixaverso.model.ProdutoEmprestimo;
import org.caixaverso.service.SimulacaoService;

import java.util.List;

@Transactional
@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoController {

    @Inject
    EntityManager entityManager;

    @Inject
    SimulacaoService simulacaoService;

    @POST
    public ProdutoEmprestimo create(ProdutoEmprestimo produto) {
        entityManager.persist(produto);
        return produto;
    }

    @POST
    @Path("/simular")
    public SimulacaoResponse simular(SimulacaoRequest request) {
        ProdutoEmprestimo produto = entityManager.find(ProdutoEmprestimo.class, request.idProduto);
        return simulacaoService.simular(produto, request.valorSolicitado, request.prazoMeses);
    }

    @GET
    public List<ProdutoEmprestimo> list() {
        return entityManager.createQuery("from ProdutoEmprestimo", ProdutoEmprestimo.class).getResultList();
    }

    @PUT
    @Path("/{id}")
    public ProdutoEmprestimo update(@PathParam("id") Long id, ProdutoEmprestimo produto) {
        ProdutoEmprestimo existing = entityManager.find(ProdutoEmprestimo.class, id);
        existing.nome = produto.nome;
        existing.taxaJurosAnual = produto.taxaJurosAnual;
        existing.prazoMaximoMeses = produto.prazoMaximoMeses;
        return existing;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        entityManager.remove(entityManager.find(ProdutoEmprestimo.class, id));
    }

}
