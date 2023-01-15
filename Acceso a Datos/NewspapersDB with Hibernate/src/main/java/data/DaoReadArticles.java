package data;

import data.hibernate.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import model.ReadArticle;

import java.util.List;

public class DaoReadArticles {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoReadArticles(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
    }

    public int save(ReadArticle readArticle) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;
        List<ReadArticle> readArticles;

        try {
            readArticles = em
                    .createNamedQuery("HQL_GET_READARTICLE_BY_READER_AND_ARTICLE", ReadArticle.class)
                    .setParameter("idReader", readArticle.getReader().getId())
                    .setParameter("idArticle", readArticle.getArticle().getId())
                    .getResultList();

            if (readArticles.isEmpty()) {
                transaction = em.getTransaction();
                transaction.begin();
                em.persist(readArticle);
                transaction.commit();
                return readArticle.getId();
            } else {
                return -1;
            }

        } catch (PersistenceException e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            return -2;
        } finally {
            if (em != null) em.close();
        }
    }

    public int update(ReadArticle readArticle) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createNamedQuery("HQL_UPDATE_READARTICLE");
            query.setParameter("idReader", readArticle.getReader().getId());
            query.setParameter("idArticle", readArticle.getArticle().getId());
            query.setParameter("rating", readArticle.getRating());
            query.executeUpdate();
            transaction.commit();
            return 1;
        } catch (PersistenceException e) {
            assert transaction != null;
            if (transaction.isActive()) transaction.rollback();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }
}
