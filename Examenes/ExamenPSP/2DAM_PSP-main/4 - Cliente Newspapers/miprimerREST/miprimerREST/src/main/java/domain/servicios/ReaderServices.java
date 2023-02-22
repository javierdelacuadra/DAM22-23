package domain.servicios;

import domain.modelo.Reader;

import java.util.List;

public interface ReaderServices {

    List<Reader> getAll();

    Reader get(String id);

    Reader add(Reader newReader);

    Reader update(Reader newReader);

    boolean delete(String id);


    // OTHER METHODS

    List<Reader> getReadersByType(String nameType);

    List<Reader> getReadersByNewspaper(String idNews);

    List<String> getOldestReaders(String idNews);
}
