package org.servidor.dao.impl.hibernate;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Singleton
public class JPAUtil {

    private final EntityManagerFactory emf;

    public JPAUtil() {
       emf=getEmf();
    }

    private EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory("caja.fuerte");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
