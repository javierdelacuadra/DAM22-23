package domain.servicios;

import dao.DaoSubscriptions;
import jakarta.inject.Inject;

public class ServiciosSubscriptions {
    private final DaoSubscriptions dao;

    @Inject
    public ServiciosSubscriptions(DaoSubscriptions dao) {
        this.dao = dao;
    }

    public boolean addSubscription(String newspaperId, String readerId) {
        return dao.save(newspaperId, readerId);
    }

    public boolean deleteSubscription(String newspaperId, String readerId) {
        return dao.remove(newspaperId, readerId);
    }

}
