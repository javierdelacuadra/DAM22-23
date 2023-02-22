package domain.servicios.impl;

import dao.impl.NewspapersDAOImpl;
import domain.modelo.Newspaper;
import domain.modelo.errores.InvalidFieldsException;
import domain.modelo.errores.NotFoundException;
import domain.servicios.NewspapersServices;
import domain.servicios.common.ServicesConstants;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

public class NewspapersServicesImpl implements NewspapersServices {

    private final NewspapersDAOImpl newspapersDAOImpl;

    @Inject
    public NewspapersServicesImpl(NewspapersDAOImpl newspapersDAOImpl) {
        this.newspapersDAOImpl = newspapersDAOImpl;
    }

    @Override
    public List<Newspaper> getAll() {
        return newspapersDAOImpl.getAll();
    }

    @Override
    public List<Newspaper> getAllSuscribed(String id) {
        return newspapersDAOImpl.getAllSuscribed(id);
    }

    @Override
    public List<Newspaper> getAllUnsuscribed(String idReader) {
        return newspapersDAOImpl.getAllUnsuscribed(idReader);
    }


    @Override
    public Newspaper get(String id) {
        return newspapersDAOImpl.get(id);
    }

    @Override
    public Newspaper add(Newspaper newspaper) {
        validations(newspaper);
        newspapersDAOImpl.add(newspaper);
        return newspaper;
    }

    private void validations(Newspaper newspaper) {
        if (newspaper.getName_newspaper().equals("") || newspaper.getRelease_date() == null) {
            throw new InvalidFieldsException(ServicesConstants.PLEASE_FILL_ALL_THE_FIELDS);
        } else if (newspaper.getRelease_date().isAfter(LocalDate.now())) {
            throw new InvalidFieldsException(ServicesConstants.THE_RELEASE_DATE_CANNOT_BE_IN_THE_FUTURE);
        }
    }

    @Override
    public Newspaper update(Newspaper news) {
        validations(news);
        if (newspapersDAOImpl.update(news) > 0) {
            return news;
        } else {
            throw new NotFoundException(ServicesConstants.NO_HAY_UN_NEWSPAPER_CON_EL_ID + news.getId());
        }
    }

    @Override
    public boolean delete(String idNews) {
        return newspapersDAOImpl.delete(idNews) > 0;
    }

}
