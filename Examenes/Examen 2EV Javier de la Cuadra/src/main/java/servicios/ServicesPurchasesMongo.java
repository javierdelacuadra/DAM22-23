package servicios;

import dao.DaoPurchasesMongo;
import jakarta.inject.Inject;
import servicios.modelo.mongo.ItemMongo;

public class ServicesPurchasesMongo {
    private final DaoPurchasesMongo dao;

    @Inject
    public ServicesPurchasesMongo(DaoPurchasesMongo dao) {
        this.dao = dao;
    }

    public int addItem(ItemMongo item) {
        return dao.add(item);
    }
}
