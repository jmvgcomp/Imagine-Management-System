package dev.jmvg.imsystem.model.dao;

import dev.jmvg.imsystem.connection.ConnectionFactory;

import javax.persistence.EntityManager;

public class DAO<T extends EntidadeBase> {

    public T salvar(T t){

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
            System.err.println(e.getMessage());
        }finally {
            em.close();
        }
        return t;
    }

    public void remover(Class<T> classe, Integer codigo){
        EntityManager em = new ConnectionFactory().getConnection();
        T t = em.find(classe, codigo);
        try{
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();

        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            em.close();
        }
    }

    public T consultarPorID(Class<T> classe, Integer codigo){
        EntityManager em = new ConnectionFactory().getConnection();
        T t = null;
        try{
            t = em.find(classe, codigo);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            em.close();
        }
        return t;
    }

}
