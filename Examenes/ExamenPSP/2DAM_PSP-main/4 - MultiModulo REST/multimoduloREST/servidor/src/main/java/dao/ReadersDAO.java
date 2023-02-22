package dao;

import org.miutils.domain.modelo.Reader;

import java.util.List;

public interface ReadersDAO {
    List<Reader> getAll();

    List<Reader> getAllByType(String  nameType);

    List<String> getNameOldestSuscriptors(String idNews);

    List<Reader> getAll(String idNews);

    Reader get(String id);

    int add(Reader newReader);

    Reader update(Reader newReader);

    int delete(String id);

}
