package domain.servicios.impl;

import dao.impl.SubscriptionsDAOImpl;
import domain.modelo.Suscription;
import domain.modelo.errores.BaseDeDatosException;
import domain.modelo.errores.InvalidFieldsException;
import domain.modelo.errores.NotFoundException;
import domain.servicios.SuscriptionsServices;
import domain.servicios.common.ServicesConstants;
import jakarta.inject.Inject;

import java.time.LocalDate;

public class SuscriptionsServicesImpl implements SuscriptionsServices {

    public static final String SINCE = " since ";
    SubscriptionsDAOImpl subscriptionsDAO;

    @Inject
    public SuscriptionsServicesImpl(SubscriptionsDAOImpl subscriptionsDAO) {
        this.subscriptionsDAO = subscriptionsDAO;
    }

    @Override
    public Suscription add(Suscription suscription) {
        suscription.setSigning_date(LocalDate.now());
        int code = subscriptionsDAO.add(suscription);
        if (code < 1) {
            throw new BaseDeDatosException(ServicesConstants.THE_SUSCRIPTION_WAS_NOT_ADDED);
        }
        return suscription;
    }

    @Override
    public Suscription update(Suscription suscription) {
        LocalDate signing = suscription.getSigning_date();
        if (signing != null && signing.isBefore(LocalDate.now().plusDays(1))) {
            int rows = subscriptionsDAO.update(suscription);
            if (rows < 1) {
                throw new NotFoundException(ServicesConstants.THE_READER + suscription.getId_reader() + ServicesConstants.IS_NOT_SUSCRIBED_TO_THE_NEWSPAPER + suscription.getId_newspaper() + SINCE + suscription.getSigning_date());
            }
            return suscription;
        }
        throw new InvalidFieldsException(ServicesConstants.IN_ORDER_TO_UPDATE_THE_SUSCRIPTION_THERE_MUST_BE_A_SIGNING_DATE);
    }
}
