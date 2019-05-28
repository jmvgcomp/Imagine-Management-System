package dev.jmvg.imsystem.model.entities;

import dev.jmvg.imsystem.model.dao.EntidadeBase;

import javax.persistence.*;

@Entity
@Table(name = "tbl_itens")
public class Itens implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ite_codigo")
    private Integer codigo;
    @Column(name = "ite_quantidade")
    private Integer quantidade;
    @Column(name = "ite_valor_parcial")
    private Integer valorPacial;
    @ManyToOne
    @JoinColumn(name = "tbl_produtos_pro_codigo", referencedColumnName = "pro_codigo")
    private Produtos produtos;
    @ManyToOne
    @JoinColumn(name = "tbl_vendas_ven_codigo", referencedColumnName = "ven_codigo")
    private Vendas vendas;

    @Override
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getValorPacial() {
        return valorPacial;
    }

    public void setValorPacial(Integer valorPacial) {
        this.valorPacial = valorPacial;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }
}
