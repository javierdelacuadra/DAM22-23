package dao;

import dao.common.SQLQueries;
import io.vavr.control.Either;
import model.Newspaper;
import model.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface NewspapersDAO {
    Either<Integer, List<Newspaper>> getAll();
    Either<Integer, List<Newspaper>> getAll(Reader currentReader);

    Either<Integer, List<Newspaper>> getAll(int idReader, boolean suscribed);
    Either<String, Newspaper> get(Newspaper news);

    int add(Newspaper newspaper);

    int update(Newspaper news);

    int delete(int id, boolean confirmed);
}
