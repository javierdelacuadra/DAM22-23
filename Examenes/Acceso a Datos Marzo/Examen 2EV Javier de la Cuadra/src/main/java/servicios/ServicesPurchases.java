package servicios;

import dao.DaoPurchases;
import dao.DaoPurchasesItems;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import servicios.modelo.hibernate.ClientsEntity;
import servicios.modelo.hibernate.PurchasesEntity;

import java.util.List;

public class ServicesPurchases {
    private final DaoPurchases daoPurchases;

    @Inject
    public ServicesPurchases(DaoPurchases daoPurchases) {
        this.daoPurchases = daoPurchases;
    }

    public Integer addPurchase(PurchasesEntity purchase) {
        return daoPurchases.add(purchase);
    }
}