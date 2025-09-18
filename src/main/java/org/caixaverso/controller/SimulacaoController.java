package org.caixaverso.controller;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.caixaverso.dto.ProdutoSimulacaoResponse;
import org.caixaverso.dto.SimulacaoRequest;
import org.caixaverso.dto.SimulacaoResponse;
import org.caixaverso.exception.EntidadeNaoEncontradaException;
import org.caixaverso.model.ProdutoEmprestimo;
import org.caixaverso.service.SimulacaoService;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Transactional
@Path("/simular")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoController {

    @Inject
    EntityManager entityManager;

    @Inject
    SimulacaoService simulacaoService;

    @POST
    @Operation(
            summary = "Simula um empréstimo",
            description = "Recebe os detalhes da simulação no corpo da requisição e retorna o resultado da simulação."
    )
    public ProdutoSimulacaoResponse simular(SimulacaoRequest request) {
        if (request == null || request.idProduto == null || request.valorSolicitado <= 0 || request.prazoMeses <= 0) {
            throw new IllegalArgumentException("Dados de simulação inválidos.");
        }
        ProdutoEmprestimo produto = entityManager.find(ProdutoEmprestimo.class, request.idProduto);
        if (produto == null) {
            throw new EntidadeNaoEncontradaException("Produto com ID " + request.idProduto + " não encontrado.");
        }
        SimulacaoResponse simulacao = simulacaoService.simular(produto, request.valorSolicitado, request.prazoMeses);
        return new ProdutoSimulacaoResponse(produto, simulacao);
    }
}
