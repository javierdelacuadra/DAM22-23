package servicios;

import data.DaoSubscriptions;
import jakarta.inject.Inject;
import model.Newspaper;

public class ServicesSubscription {

    private final DaoSubscriptions daoSubscriptions;

    @Inject
    public ServicesSubscription(DaoSubscriptions daoSubscriptions) {
        this.daoSubscriptions = daoSubscriptions;
    }

    public Integer addSubscription(Newspaper newspaper, int id) {
        return daoSubscriptions.save(newspaper, id);
    }

    public Integer removeSubscription(Newspaper newspaper, int id) {
        return daoSubscriptions.remove(newspaper, id);
    }
}