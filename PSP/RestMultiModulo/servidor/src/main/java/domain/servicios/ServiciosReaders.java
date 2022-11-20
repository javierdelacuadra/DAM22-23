package domain.servicios;

import dao.DaoReaders;
import jakarta.inject.Inject;
import modelo.Reader;

import java.util.List;

public class ServiciosReaders {

    private final DaoReaders dao;

    @Inject
    public ServiciosReaders(DaoReaders dao) {
        this.dao = dao;
    }

    public List<Reader> getAllReaders() {
        return dao.getAll();
    }

    public Reader addReader(Reader reader) {
        return dao.save(reader);
    }

    public Reader updateReader(Reader reader) {
        return dao.update(reader);
    }

    public boolean deleteReader(String id) {
        return dao.delete(id);
    }

    public Reader getReader(String id) {
        return dao.get(id);
    }

    public List<Reader> getReadersByArticleType(String type) {
        return dao.getReadersByArticleType(type);
    }

    public List<Reader> getReadersByNewspaper(String idNewspaper) {
        return dao.getReadersByNewspaper(idNewspaper);
    }
}
