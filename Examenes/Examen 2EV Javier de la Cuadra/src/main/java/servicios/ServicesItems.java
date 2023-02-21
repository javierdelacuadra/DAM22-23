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
        Either<Integer, List<PurchasesEntity>> either = daoPurchases.get(clientID);
        List<PurchasesEntity> list = either.get();
        List<Integer> purchasesIDs = new ArrayList<>();
        list.forEach(purchasesEntities -> purchasesIDs.add(purchasesEntities.getId()));
        List<PurchasesItemsEntity> purchasedItems = daoPurchasesItems.get(purchasesIDs).get();
        List<Integer> itemsIDs = new ArrayList<>();
        purchasedItems.forEach(purchasesEntities -> itemsIDs.add(purchasesEntities.getItems().getId()));
        List<ItemsEntity> items = dao.get(itemsIDs).get();
        items.removeIf(Objects::isNull);
        items.removeIf(r -> r.getName() == null);
        items.removeIf(r -> list.stream().filter(r1 -> r1.getId() == (r.getId())).count() > 1);
        items.sort(Comparator.comparingInt(ItemsEntity::getId));
        return items;
    }
}
