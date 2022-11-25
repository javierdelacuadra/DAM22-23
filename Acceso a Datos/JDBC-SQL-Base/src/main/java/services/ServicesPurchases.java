package services;

import dao.DaoPurchases;
import jakarta.inject.Inject;
import model.Purchase;
import model.PurchasesItem;

public class ServicesPurchases {

    private final DaoPurchases dao;

    @Inject
    public ServicesPurchases(DaoPurchases dao) {
        this.dao = dao;
    }

    public int savePurchase(Purchase purchase) {
        return dao.save(purchase);
    }

    public int payPurchase(int clientID, int purchaseID) {
        return dao.update(clientID, purchaseID);
    }

    public int updatePurchase(PurchasesItem purchasesItem) {
        return dao.update(purchasesItem);
    }
}