package data;

import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import model.Article;

import java.util.ArrayList;
import java.util.List;

public class DaoArticles {

    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoArticles(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
    }

    public Either<Integer, List<Article>> getAll() {
        List<Article> articles = null;
        em = jpaUtil.getEntityManager();

        try {
            articles = em
                    .createNamedQuery("HQL_GET_ALL_ARTICLES", Article.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        assert articles != null;
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }

    public Either<Integer, List<Article>> getAll(Integer id) {
        List<Article> articles = null;
        em = jpaUtil.getEntityManager();

        try {
            articles = em
                    .createNamedQuery("HQL_GET_ARTICLE_BY_ID", Article.class)
                    .setParameter("id", id)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        assert articles != null;
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }

    public Either<Integer, List<Article>> getAll(String type) {
        List<Article> articles = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            articles = em
                    .createNamedQuery("HQL_GET_ARTICLES_BY_TYPE", Article.class)
                    .setParameter("description", type)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }


    public Integer add(Article article) {
        em = jpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(article);
            transaction.commit();
            return 1;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Integer delete(Article article) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.remove(em.merge(article));
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Integer delete(Integer id) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.createNamedQuery("HQL_DELETE_ARTICLE_BY_NEWSPAPER_ID")
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public Integer update(Article article) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(article);
            tx.commit();
            return 1;
        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Article> getAllWithTypes() {
        em = jpaUtil.getEntityManager();
        List<Article> articles = new ArrayList<>();

        try {
            articles = em
                    .createNamedQuery("HQL_GET_ALL_ARTICLES_AND_TYPES", Article.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return articles;
    }
}