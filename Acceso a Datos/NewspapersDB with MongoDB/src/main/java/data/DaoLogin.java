package data;

import data.hibernate.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import model.Login;

public class DaoLogin {

    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoLogin(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
        this.em = jpaUtil.getEntityManager();
    }

    public Integer login(Login login) {
        em = jpaUtil.getEntityManager();
        Login user;

        try {
            user = em
                    .createNamedQuery("HQL_GET_LOGIN", Login.class)
                    .setParameter("name", login.getName())
                    .setParameter("password", login.getPassword())
                    .getSingleResult();
        } catch (PersistenceException e) {
            return -2;
        } finally {
            if (em != null) em.close();
        }
        return user.getReader().getId();

    }
}
