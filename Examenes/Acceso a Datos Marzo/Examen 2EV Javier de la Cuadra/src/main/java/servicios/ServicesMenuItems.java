package servicios;

import dao.DaoMenuItems;
import dao.DaoOrders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import servicios.modelo.hibernate.MenuItemsEntity;

import java.util.ArrayList;
import java.util.List;

public class ServicesMenuItems {
    private final DaoMenuItems dao;
    private final DaoOrders daoPurchases;

    @Inject
    public ServicesMenuItems(DaoMenuItems dao, DaoOrders daoPurchases) {
        this.dao = dao;
        this.daoPurchases = daoPurchases;
    }

    public List<MenuItemsEntity> getItemsPurchasedByClientID(int clientID) {
        Either<Integer, List<MenuItemsEntity>> purchases = daoPurchases.get(clientID);
        if (purchases.isLeft()) {
            return new ArrayList<>();
        } else {
            return purchases.get();
        }
    }

    public MenuItemsEntity getItemByName(String name) {
        return dao.get(name).get();
    }
}
