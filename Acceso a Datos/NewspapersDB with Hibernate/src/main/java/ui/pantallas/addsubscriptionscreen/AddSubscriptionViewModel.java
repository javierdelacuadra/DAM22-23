package ui.pantallas.addsubscriptionscreen;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
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

    public Integer addSubscription(Newspaper newspaper, int id) {
        return servicesSubscription.addSubscription(newspaper, id);
    }

    public Integer removeSubscription(Newspaper newspaper, int id) {
        return servicesSubscription.removeSubscription(newspaper, id);
    }
}