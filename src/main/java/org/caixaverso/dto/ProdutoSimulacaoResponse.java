package org.caixaverso.dto;

import org.caixaverso.model.ProdutoEmprestimo;
import java.util.Objects;

public class ProdutoSimulacaoResponse {
    public ProdutoEmprestimo produto;
    public SimulacaoResponse simulacao;

    public ProdutoSimulacaoResponse(ProdutoEmprestimo produto, SimulacaoResponse simulacao) {
        this.produto = produto;
        this.simulacao = simulacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoSimulacaoResponse that = (ProdutoSimulacaoResponse) o;
        return Objects.equals(produto, that.produto) &&
                Objects.equals(simulacao, that.simulacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto, simulacao);
    }
}