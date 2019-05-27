package dev.jmvg.imsystem.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_fornecedores")
public class Fornecedores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "for_codigo")
    private int codigo;
    @Column(name = "for_nome")
    private String nome;
    @Column(name = "for_descricao")
    private String descricao;
    @Column(name = "for_cnpj")
    private String cnpj;
    @Column(name = "for_telefone")
    private String telefone;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
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
}
