package dev.jmvg.imsystem.model.entities;

import dev.jmvg.imsystem.model.dao.EntidadeBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_fornecedores")
public class Fornecedores implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "for_codigo")
    private Integer codigo;
    @Column(name = "for_nome")
    private String nome;
    @Column(name = "for_descricao")
    private String descricao;
    @Column(name = "for_cnpj")
    private String cnpj;
    @Column(name = "for_telefone")
    private String telefone;

    @Override
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Fornecedores{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
