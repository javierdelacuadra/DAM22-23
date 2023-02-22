package domain.servicios.impl;

import dao.impl.ReadersDAOImpl;
import domain.modelo.errores.InvalidFieldsException;
import domain.servicios.ReaderServices;
import domain.servicios.common.ServicesConstants;
import jakarta.inject.Inject;
import org.miutils.domain.modelo.Reader;

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
    public Reader update(Reader newReader) {
        // llega sin login porque las credenciales no se pueden actualizar
        if (newReader.getName().isEmpty() || newReader.getBirthDate() == null) {
            throw new InvalidFieldsException(ServicesConstants.PLEASE_FILL_ALL_THE_FIELDS);
        } else if (newReader.getBirthDate().isAfter(LocalDate.now().minusYears(1))) {
            throw new InvalidFieldsException(ServicesConstants.THE_NEW_READER_MUST_BE_AT_LEAST_1_YEARS_OLD);
        } else {
            return readersDAO.update(newReader);
        }


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

