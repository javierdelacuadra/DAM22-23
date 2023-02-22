package dao;

import io.vavr.control.Either;
import model.Reader;

public interface LoginDAO {

    Either<Integer, Reader> login(String user, String password);

    void closePool();
}
