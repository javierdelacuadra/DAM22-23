package org.example.services.impl;

import jakarta.inject.Inject;
import org.example.dao.PurchaseDao;
import org.example.model.hibernate.Purchase;

public class PurchaseServicesImpl implements org.example.services.PurchaseServices {
    private PurchaseDao purchaseDao;

    @Inject
    public PurchaseServicesImpl(PurchaseDao purchaseDao){
        this.purchaseDao = purchaseDao;
    }

    @Override
    public int add(Purchase purchase){
        return purchaseDao.add(purchase);
    }
}
