package dao;

import model.Newspaper;
import model.Reader;
import io.vavr.control.Either;
import model.TypeArt;

import java.util.List;

public interface ReadersDAO {
    Either<Integer, List<Reader>> getAll();

    Either<Integer, List<Reader>> getAll(TypeArt typeArt);

    Either<Integer, List<String>> getAll(Newspaper newspaper, int limit);

    Either<Integer, List<Reader>> getAll(Newspaper newspaper);

    Either<Integer, Reader> get(int id);

    int add(Reader newReader);

    int update(Reader newReader);

    int delete(int id);

}
