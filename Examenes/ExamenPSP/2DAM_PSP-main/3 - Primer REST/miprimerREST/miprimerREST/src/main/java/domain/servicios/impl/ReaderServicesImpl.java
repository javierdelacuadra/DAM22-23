package domain.servicios.impl;

import dao.impl.ReadersDAOImpl;
import domain.modelo.Reader;
import domain.modelo.errores.InvalidFieldsException;
import domain.servicios.ReaderServices;
import domain.servicios.common.ServicesConstants;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

public class ReaderServicesImpl implements ReaderServices {
    private final ReadersDAOImpl readersDAO;

    @Inject
    public ReaderServicesImpl(ReadersDAOImpl readersDAO) {
        this.readersDAO = readersDAO;
    }

    @Override
    public List<Reader> getAll() {
        return readersDAO.getAll();
    }

    @Override
    public Reader get(String id) {
        return readersDAO.get(id);
    }


    @Override
    public boolean delete(String id) {
        return readersDAO.delete(id) > 0;
    }


    @Override
    public Reader add(Reader newReader) {
        validations(newReader);
        if (readersDAO.add(newReader) == 1) {
            return newReader;
        } else {
            throw new InvalidFieldsException(ServicesConstants.NO_SE_PUDO_AGREGAR_EL_LECTOR);
        }
    }

    private void validations(Reader newReader) {
        if (newReader.getName().equals("") || newReader.getBirthDate() == null || newReader.getLogin().getUser().equals("") || newReader.getLogin().getPassword().equals("")) {
            throw new InvalidFieldsException(ServicesConstants.PLEASE_FILL_ALL_THE_FIELDS);
        } else if (newReader.getBirthDate().isAfter(LocalDate.now().minusYears(5))) {
            throw new InvalidFieldsException(ServicesConstants.THE_NEW_READER_MUST_BE_AT_LEAST_5_YEARS_OLD);
        } else if (newReader.getLogin().getPassword().length() > 15
                || newReader.getLogin().getUser().length() > 15) {
            throw new InvalidFieldsException(ServicesConstants.REMEMBER_THAT_THE_CREDENTIALS_CAN_NOT_BE_LONGER_THAN_15_CHARACTERS);
        }
    }

    @Override
    public Reader update(Reader newReader) {
        validations(newReader);
        return readersDAO.update(newReader);

    }


    @Override
    public List<Reader> getReadersByType(String nameType) {
        return readersDAO.getAllByType(nameType);

    }

    @Override
    public List<Reader> getReadersByNewspaper(String idNews) {
        return readersDAO.getAll(idNews);
    }

    @Override
    public List<String> getOldestReaders(String idNews) {
        return readersDAO.getNameOldestSuscriptors(idNews);
    }


}

