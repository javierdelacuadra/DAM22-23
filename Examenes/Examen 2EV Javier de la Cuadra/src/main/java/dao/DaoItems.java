package dao;

import dao.hibernate.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import servicios.modelo.hibernate.ItemsEntity;

import java.util.ArrayList;
import java.util.List;

public class DaoItems {

    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public DaoItems() {
        jpaUtil = new JPAUtil();
        em = jpaUtil.getEntityManager();
    }

    public Either<Integer, List<ItemsEntity>> get(List<Integer> itemsIDs) {
        List<ItemsEntity> tempItems;
        List<ItemsEntity> items = new ArrayList<>();
        em = jpaUtil.getEntityManager();

        try {
            for (Integer itemID : itemsIDs) {
                tempItems = em
                        .createNamedQuery("HQL_GET_ITEMS_BY_ID", ItemsEntity.class)
                        .setParameter("id", itemID)
                        .getResultList();
                items.addAll(tempItems);
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }

        return items.isEmpty() ? Either.left(-1) : Either.right(items);
    }
}
