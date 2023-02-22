package domain.servicios;

import domain.modelo.TypeArt;
import io.vavr.control.Either;

import java.util.List;

public interface TypeServices {
    List<TypeArt> getAllTypes();

    Either<String, TypeArt> get(TypeArt typeArt);

    int add(TypeArt typeArt);

    int update(TypeArt typeArt);

    int delete(TypeArt typeArt);
}
