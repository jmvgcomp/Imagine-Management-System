package dev.jmvg.imsystem.model.entities;

import dev.jmvg.imsystem.model.dao.EntidadeBase;

import javax.persistence.*;

@Entity
@Table(name = "tbl_clientes")
public class Clientes implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_codigo")
    private Integer codigo;
    @Column(name = "cli_representante")
    private String representante;
    @Column(name = "cli_empresa")
    private String empresa;
    @Column(name = "cli_cnpj")
    private String cnpj;
    @Column(name = "cli_telefone")
    private String telefone;

    @Override
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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
