package dao;

import dao.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import servicios.modelo.hibernate.MenuItemsEntity;

public class DaoMenuItems {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoMenuItems() {
        jpaUtil = new JPAUtil();
        em = jpaUtil.getEntityManager();
    }

    public Either<Integer, MenuItemsEntity> get(String name) {
        MenuItemsEntity item;
        em = jpaUtil.getEntityManager();

        try {
            item = em
                    .createNamedQuery("HQL_GET_MENU_ITEM_BY_NAME", MenuItemsEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return Either.left(-1);
        } finally {
            if (em != null) em.close();
        }

        return Either.right(item);
    }
}
