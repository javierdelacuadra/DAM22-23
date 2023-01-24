package data.hibernate;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


/**
 * @author Javier
 */

@Singleton
public class JPAUtil {

    private EntityManagerFactory emf;

    public JPAUtil() {
        emf = getEmf();
    }

    private EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory("NewspapersDB");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
