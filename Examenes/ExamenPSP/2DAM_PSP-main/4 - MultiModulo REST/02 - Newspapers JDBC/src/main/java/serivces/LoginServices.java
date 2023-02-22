package serivces;

import io.vavr.control.Either;
import model.Reader;

public interface LoginServices {


    Either<String, Reader> login(String user, String password);

    void closeProgram();
}
