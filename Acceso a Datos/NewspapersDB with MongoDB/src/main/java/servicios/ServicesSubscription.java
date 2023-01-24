package servicios;

import data.DaoSubscriptions;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Reader;
import model.Subscription;

import java.util.List;

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

    public Either<Integer, List<Subscription>> get(Reader reader) {
        return daoSubscriptions.get(reader);
    }
}