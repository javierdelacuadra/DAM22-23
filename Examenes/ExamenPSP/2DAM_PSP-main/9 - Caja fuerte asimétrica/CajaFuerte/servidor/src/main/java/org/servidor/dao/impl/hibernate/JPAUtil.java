package org.servidor.dao.impl.hibernate;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Singleton
public class JPAUtil {

    public static final String CAJA_FUERTE = "caja.fuerte";
    private final EntityManagerFactory emf;

    public JPAUtil() {
       emf=getEmf();
    }

    private EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory(CAJA_FUERTE);
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
