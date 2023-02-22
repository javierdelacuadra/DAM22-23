package serivces;

import io.vavr.control.Either;
import model.TypeArt;

import java.util.List;

public interface TypeServices {
    Either<String, List<TypeArt>> getAllTypes();

    Either<String, TypeArt> get(TypeArt typeArt);

    int add(TypeArt typeArt);

    int update(TypeArt typeArt);

    int delete(TypeArt typeArt);
}
