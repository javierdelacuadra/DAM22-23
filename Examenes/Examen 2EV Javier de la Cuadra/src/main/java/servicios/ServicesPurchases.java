package servicios;

import dao.DaoPurchases;
import dao.DaoPurchasesItems;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import servicios.modelo.hibernate.ClientsEntity;
import servicios.modelo.hibernate.PurchasesEntity;

import java.util.List;

public class ServicesPurchases {
    private final DaoPurchases dao;
    private final DaoPurchasesItems daoPurchasesItems;

    @Inject
    public ServicesPurchases(DaoPurchases dao, DaoPurchasesItems daoPurchasesItems) {
        this.dao = dao;
        this.daoPurchasesItems = daoPurchasesItems;
    }

    public Integer addPurchase(PurchasesEntity purchase) {
        int id = dao.addPurchase(purchase);
        purchase.setId(id);
        for (int i = 0; i < purchase.getPurchasesItems().size(); i++) {
            purchase.getPurchasesItems().get(i).setPurchases(new PurchasesEntity());
            purchase.getPurchasesItems().get(i).getPurchases().setId(id);
        }
        return daoPurchasesItems.addPurchasedItems(purchase.getPurchasesItems());
    }

    public ClientsEntity getPurchaseByClientId(ClientsEntity client) {
        Either<Integer, List<PurchasesEntity>> either = dao.get(client.getId());
        List<PurchasesEntity> list = either.get();
        client.setPurchasesById(list);
        return client;
    }
}