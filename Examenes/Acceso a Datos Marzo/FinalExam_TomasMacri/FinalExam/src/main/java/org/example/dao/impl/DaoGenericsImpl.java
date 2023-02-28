package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.bson.conversions.Bson;
import org.example.common.NumericConstants;
import org.example.dao.common.DAOConstants;
import org.example.dao.impl.connections.DBConnection;
import org.example.dao.impl.connections.JPAUtil;

public class DaoGenericsImpl implements org.example.dao.DaoGenerics {

    @Override
    public int add(JPAUtil jpaUtil, Object o) {
        EntityManager em = getEntityManager(jpaUtil);
        EntityTransaction tx = getEntityTransaction(em);
        tx.begin();
        try {
            em.persist(o);
            tx.commit();
            return 1;
        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            if (e.getCause().toString().contains(DAOConstants.CONSTRAINT_VIOLATION_EXCEPTION)){
                return NumericConstants.INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION;
            }
            return NumericConstants.DB_EXCEPTION_CODE;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        } finally {
            em.close();
        }
    }

    @Override
    public int update(JPAUtil jpaUtil, Object o) {
        EntityManager em = getEntityManager(jpaUtil);
        EntityTransaction tx = getEntityTransaction(em);
        tx.begin();
        try {
            //Find to reattach and then modify value can also be used
            em.merge(o);
            tx.commit();
            return 1;
        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            return NumericConstants.DB_EXCEPTION_CODE;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        } finally {
            em.close();
        }
    }

    @Override
    public int update(DBConnection dbConnection, Bson filter, Bson update) {
        int result;
        try {
            result = Integer.parseInt(dbConnection.getEst().updateOne(filter, update).getModifiedCount()+"");
        } catch (Exception e) {
            result = NumericConstants.DB_EXCEPTION_CODE;
        }
        return result;
    }


    @Override
    public int delete(JPAUtil jpaUtil, Object o) {
        EntityManager em = getEntityManager(jpaUtil);
        EntityTransaction tx = getEntityTransaction(em);
        tx.begin();
        try {
            //Reattach the object before removing
            em.remove(em.merge(o));
            tx.commit();
            return 1;
        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            if (e.getCause().toString().contains(DAOConstants.CONSTRAINT_VIOLATION_EXCEPTION)){
                return NumericConstants.INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION;
            }
            return NumericConstants.DB_EXCEPTION_CODE;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE;
        } finally {
            em.close();
        }
    }

    private EntityTransaction getEntityTransaction(EntityManager em) {
        return em.getTransaction();
    }

    private EntityManager getEntityManager(JPAUtil jpaUtil) {
        return jpaUtil.getEntityManager();
    }
}
