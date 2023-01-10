package data;

import data.hibernate.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import model.ReadArticle;

public class DaoReadArticles {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoReadArticles(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
    }

    public int saveReadArticle(ReadArticle readArticle) {
        //TODO: check si ha rateado ya el articulo devuelve -2

        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(readArticle);
            transaction.commit();
            return 1;
        } catch (PersistenceException e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }
}
