package dev.jmvg.imsystem.model.dao;

import dev.jmvg.imsystem.Exceptions.DAOException;
import dev.jmvg.imsystem.model.connection.ConnectionFactory;
import dev.jmvg.imsystem.model.entities.Fornecedores;

import javax.persistence.EntityManager;
import java.util.List;

public class DAO<T extends EntidadeBase> {

    public T salvar(T t) throws DAOException {

        EntityManager em = new ConnectionFactory().getConnection();
        try{

            em.getTransaction().begin();
            if(t.getCodigo() == null){
                em.persist(t);
            }else{
                em.merge(t);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new DAOException("Erro em salvar ou atualizar: "+e.getMessage());
        }finally {
            em.close();
        }
        return t;
    }

    public void remover(Class<T> classe, Integer codigo) throws DAOException {
        EntityManager em = new ConnectionFactory().getConnection();
        T t = em.find(classe, codigo);
        try{
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();

        }catch (Exception e){
            throw new DAOException("Erro na remoção do elemento: "+e.getMessage());
        }finally {
            em.close();
        }
    }

    public T consultarPorID(Class<T> classe, Integer codigo) throws DAOException {
        EntityManager em = new ConnectionFactory().getConnection();
        T t;
        try{
            t = em.find(classe, codigo);
        }catch (Exception e){
            throw new DAOException("Erro na consulta do elemento: "+e.getMessage());
        }finally {
            em.close();
        }
        return t;
    }

    public List<T> listarTodos(T t) throws DAOException{
        EntityManager em = new ConnectionFactory().getConnection();
        List<T> list = null;
        try {
            list = em.createQuery("from "+t.getClass().getSimpleName()).getResultList();
        }catch (Exception e){
            throw new DAOException("Erro na listagem: "+e.getMessage());
        }finally {
            em.close();
        }
        return list;
    }
}
