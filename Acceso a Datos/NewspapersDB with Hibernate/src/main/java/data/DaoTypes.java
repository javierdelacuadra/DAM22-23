package data;

import data.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import model.ArticleType;

import java.util.ArrayList;
import java.util.List;

public class DaoTypes {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoTypes(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
    }

    public Either<Integer, List<ArticleType>> getAll() {
        List<ArticleType> types = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            types = em
                    .createNamedQuery("HQL_GET_ALL_TYPES", ArticleType.class)
                    .getResultList();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return types.isEmpty() ? Either.left(-1) : Either.right(types);
    }
}
