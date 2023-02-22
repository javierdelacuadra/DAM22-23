package org.servidor.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.servidor.dao.DaoGenerics;
import org.servidor.dao.common.DaoConstants;
import org.servidor.dao.impl.hibernate.JPAUtil;
import org.servidor.domain.errores.BaseDeDatosException;
import org.servidor.domain.errores.DataIntegrityException;
import org.servidor.domain.errores.OtherErrorException;

public class DaoGenericsImpl implements DaoGenerics {

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
            if (e.getCause().toString().contains(DaoConstants.CONSTRAINT_VIOLATION_EXCEPTION)){
                throw new DataIntegrityException(DaoConstants.ESE_NOMBRE_YA_ESTA_EN_USO_PRUEBA_OTRO);
            }
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new OtherErrorException(e.getMessage());
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
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new OtherErrorException(e.getMessage());
        } finally {
            em.close();
        }
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
            if (e.getCause().toString().contains(DaoConstants.CONSTRAINT_VIOLATION_EXCEPTION)){
                throw new DataIntegrityException(e.getMessage());
            }
            throw new BaseDeDatosException(e.getMessage());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new OtherErrorException(e.getMessage());
        } finally {
            em.close();
        }
    }

    private static EntityTransaction getEntityTransaction(EntityManager em) {
        return em.getTransaction();
    }

    private static EntityManager getEntityManager(JPAUtil jpaUtil) {
        return jpaUtil.getEntityManager();
    }
}
