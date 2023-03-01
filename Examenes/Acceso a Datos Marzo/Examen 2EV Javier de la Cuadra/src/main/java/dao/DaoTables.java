package dao;

import dao.hibernate.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import servicios.modelo.hibernate.TablesEntity;

public class DaoTables {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoTables() {
        jpaUtil = new JPAUtil();
        em = jpaUtil.getEntityManager();
    }

    public TablesEntity get(int id) {
        TablesEntity client = new TablesEntity();
        em = jpaUtil.getEntityManager();

        try {
            client = em
                    .createNamedQuery("HQL_GET_TABLE_BY_ID", TablesEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();

        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return client;
    }
}
