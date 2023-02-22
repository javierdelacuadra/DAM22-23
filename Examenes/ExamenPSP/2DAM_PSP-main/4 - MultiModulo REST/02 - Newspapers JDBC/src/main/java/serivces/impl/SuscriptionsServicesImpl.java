package serivces.impl;

import common.NumericConstants;
import dao.impl.SubscriptionsDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Suscription;
import serivces.SuscriptionsServices;
import serivces.common.ServicesConstants;

import java.time.LocalDate;

public class SuscriptionsServicesImpl implements SuscriptionsServices {

    SubscriptionsDAOImpl subscriptionsDAO;

    @Inject
    public SuscriptionsServicesImpl(SubscriptionsDAOImpl subscriptionsDAO) {
        this.subscriptionsDAO = subscriptionsDAO;
    }

    @Override
    public Either<String, String> changeSuscription(int idReader, int idNews, boolean isAlreadySuscribed) {
        Suscription suscription = new Suscription(idReader, idNews);
        int code;
        if (isAlreadySuscribed) {
            suscription.setCancellation_date(LocalDate.now());
            code = subscriptionsDAO.update(suscription);
        } else {
            suscription.setSigning_date(LocalDate.now());
            code = subscriptionsDAO.add(suscription);
            if (code == NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION) {
                code = subscriptionsDAO.update(suscription);
            }
        }
        return switch (code) {
            case NumericConstants.DB_EXCEPTION_CODE -> Either.left(ServicesConstants.ERROR_CHANGING_SUSCRIPTION);
            case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                    Either.left(ServicesConstants.ERROR_CHANGING_SUSCRIPTION + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
            case 1 ->
                    Either.right(ServicesConstants.YOU_HAVE_SUCCESSFULLY + (isAlreadySuscribed ? ServicesConstants.UNSUSCRIBED_FROM : ServicesConstants.SUSCRIBED_TO) + ServicesConstants.THE_NEWSPAPER);
            default ->
                    Either.left(ServicesConstants.ERROR_CHANGING_SUSCRIPTION + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
        };
    }
}
