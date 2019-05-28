package dev.jmvg.imsystem.model.entities;


import dev.jmvg.imsystem.model.dao.EntidadeBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "tbl_vendas")
public class Vendas implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ven_codigo")
    private Integer codigo;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "ven_horario")
    private Date horario;
    @Column(name = "ven_valor_total")
    private Double valorTotal;
    @ManyToOne
    @JoinColumn(name = "tbl_funcionarios_fun_codigo", referencedColumnName = "fun_codigo")
    private Funcionarios funcionarios;

    @Override
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Funcionarios getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(Funcionarios funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public String toString() {
        return "Vendas{" +
                "codigo=" + codigo +
                ", horario=" + horario +
                ", valorTotal=" + valorTotal +
                ", funcionarios=" + funcionarios +
                '}';
    }
}
