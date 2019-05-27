package dev.jmvg.imsystem.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_produtos")
public class Produtos {
    @Id
    @Column(name = "pro_codigo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    @Column(name = "pro_descricao")
    private String descricao;
    @Column(name = "pro_valor")
    private Double valor;
    @Column(name = "pro_quantidade")
    private int quantidade;


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
