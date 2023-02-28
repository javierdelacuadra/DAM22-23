package servicios;

import dao.DaoItems;
import dao.DaoPurchases;
import dao.DaoPurchasesItems;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import servicios.modelo.hibernate.ItemsEntity;
import servicios.modelo.hibernate.PurchasesEntity;
import servicios.modelo.hibernate.PurchasesItemsEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ServicesItems {

    private final DaoItems dao;
    private final DaoPurchases daoPurchases;
    private final DaoPurchasesItems daoPurchasesItems;

    @Inject
    public ServicesItems(DaoItems dao, DaoPurchases daoPurchases, DaoPurchasesItems daoPurchasesItems) {
        this.dao = dao;
        this.daoPurchases = daoPurchases;
        this.daoPurchasesItems = daoPurchasesItems;
    }

    public List<ItemsEntity> getPurchaseByClientId(int clientID) {
        Either<Integer, List<ItemsEntity>> purchases = daoPurchases.get(clientID);
        if (purchases.isLeft()) {
            return new ArrayList<>();
        } else {
            return purchases.get();
        }
    }

    public ItemsEntity getItemByName(String name) {
        return dao.get(name).get();
    }
}
