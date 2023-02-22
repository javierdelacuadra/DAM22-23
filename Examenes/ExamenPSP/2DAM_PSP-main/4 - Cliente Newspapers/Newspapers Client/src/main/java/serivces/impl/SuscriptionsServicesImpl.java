package serivces.impl;

import dao.impl.SubscriptionsDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;
import model.Reader;
import serivces.SuscriptionsServices;
import serivces.common.ServicesConstants;

import java.util.List;

public class SuscriptionsServicesImpl implements SuscriptionsServices {

    SubscriptionsDAOImpl subscriptionsDAO;

    @Inject
    public SuscriptionsServicesImpl(SubscriptionsDAOImpl subscriptionsDAO) {
        this.subscriptionsDAO = subscriptionsDAO;
    }

}
