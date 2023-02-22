package dao;

import io.vavr.control.Either;
import model.TypeArt;

import java.util.List;

public interface TypeDAO {
    Either<Integer, List<TypeArt>> getAll();

    Either<String, TypeArt> get(TypeArt typeArt);

    int add(TypeArt typeArt);

    int update(TypeArt typeArt);

    int delete(TypeArt typeArt);
}
