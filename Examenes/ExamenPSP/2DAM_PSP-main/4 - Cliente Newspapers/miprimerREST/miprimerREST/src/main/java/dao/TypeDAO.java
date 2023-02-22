package dao;

import domain.modelo.TypeArt;
import io.vavr.control.Either;

import java.util.List;

public interface TypeDAO {
    List<TypeArt> getAll();

    Either<String, TypeArt> get(TypeArt typeArt);

    int add(TypeArt typeArt);

    int update(TypeArt typeArt);

    int delete(TypeArt typeArt);
}
