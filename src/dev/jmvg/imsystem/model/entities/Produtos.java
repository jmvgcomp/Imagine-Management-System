package dev.jmvg.imsystem.model.entities;

import dev.jmvg.imsystem.model.dao.EntidadeBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_produtos")
public class Produtos implements EntidadeBase {
    @Id
    @Column(name = "pro_codigo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @Column(name = "pro_descricao")
    private String descricao;
    @Column(name = "pro_valor")
    private Double valor;
    @Column(name = "pro_quantidade")
    private Integer quantidade;
    @Column(name = "pro_observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "tbl_fornecedores_for_codigo", referencedColumnName = "for_codigo")
    private Fornecedores fornecedores;

    @Override
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Fornecedores getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(Fornecedores fornecedores) {
        this.fornecedores = fornecedores;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }



    @Override
    public String toString() {
        return "Produtos{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", fornecedores=" + fornecedores +
                '}';
    }
}
