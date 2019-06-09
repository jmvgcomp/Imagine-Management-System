package dev.jmvg.imsystem.model.dao;

import dev.jmvg.imsystem.model.connection.ConnectionFactory;
import dev.jmvg.imsystem.model.entities.Funcionarios;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class FuncionarioDAO extends DAO<Funcionarios> {
    EntityManager em = new ConnectionFactory().getConnection();
    public Funcionarios getFuncionario(String cpf, String senha) {

        try {
            Funcionarios funcionarios = (Funcionarios) em
                    .createQuery(
                            "SELECT f FROM Funcionarios f where f.cpf =:cpf and f.senha =:senha")
                    .setParameter("cpf", cpf)
                    .setParameter("senha", senha).getSingleResult();
            return funcionarios;
        } catch (NoResultException e) {
            return null;
        }
    }

}
