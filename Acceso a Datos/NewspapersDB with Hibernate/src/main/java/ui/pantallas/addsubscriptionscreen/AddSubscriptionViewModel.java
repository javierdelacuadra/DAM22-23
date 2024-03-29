package ui.pantallas.addsubscriptionscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import model.Subscription;
import servicios.ServicesNewspaperSQL;
import servicios.ServicesSubscription;

public class AddSubscriptionViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesSubscription servicesSubscription;

    @Inject
    public AddSubscriptionViewModel(ServicesNewspaperSQL servicesNewspaperSQL, ServicesSubscription servicesSubscription) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesSubscription = servicesSubscription;
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

    public Integer addSubscription(Subscription subscription) {
        return servicesSubscription.addSubscription(subscription);
    }

    public Integer removeSubscription(Subscription subscription) {
        return servicesSubscription.removeSubscription(subscription);
    }
}