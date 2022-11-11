package domain.servicios;

import dao.DaoSubscriptions;
import dao.modelo.Newspaper;
import dao.modelo.Subscription;
import jakarta.inject.Inject;

public class ServiciosSubscriptions {
    private final DaoSubscriptions dao;

    @Inject
    public ServiciosSubscriptions(DaoSubscriptions dao) {
        this.dao = dao;
    }

    public boolean addSubscription(Newspaper newspaper, String id) {
        return dao.save(newspaper, id);
    }

    public boolean deleteSubscription(Newspaper newspaper, String id) {
        return dao.remove(newspaper, id);
    }

}
