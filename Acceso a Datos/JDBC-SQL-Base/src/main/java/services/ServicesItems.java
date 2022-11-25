package services;

import dao.DaoItems;
import jakarta.inject.Inject;

public class ServicesItems {
    private final DaoItems dao;

    @Inject
    public ServicesItems(DaoItems dao) {
        this.dao = dao;
    }

    public String mostPurchasedItem() {
        return dao.mostPurchasedItem();
    }
}