package dev.jmvg.imsystem.model.entities;

import dev.jmvg.imsystem.model.dao.EntidadeBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_funcionarios")
public class Funcionarios implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fun_codigo")
    private Integer codigo;
    @Column(name = "fun_nome")
    private String nome;
    @Column(name = "fun_cpf")
    private String cpf;
    @Column(name = "fun_telefone")
    private String telefone;
    @Column(name = "fun_senha")
    private String senha;
    @Column(name = "fun_nivel")
    private String nivel;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Funcionarios{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", senha='" + senha + '\'' +
                ", nivel='" + nivel + '\'' +
                '}';
    }
}
