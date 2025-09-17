package org.caixaverso.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProdutoEmprestimo {
    @Id
    public Long id;
    public String nome;
    public double taxaJurosAnual;
    public int prazoMaximoMeses;
}
