package servicios;

import data.DaoSubscriptions;
import jakarta.inject.Inject;
import model.Subscription;

public class ServicesSubscription {

    private final DaoSubscriptions daoSubscriptions;

    @Inject
    public ServicesSubscription(DaoSubscriptions daoSubscriptions) {
        this.daoSubscriptions = daoSubscriptions;
    }

    public Integer addSubscription(Subscription subscription) {
        return daoSubscriptions.save(subscription);
    }

    public Integer removeSubscription(Subscription subscription) {
        return daoSubscriptions.update(subscription);
    }
}