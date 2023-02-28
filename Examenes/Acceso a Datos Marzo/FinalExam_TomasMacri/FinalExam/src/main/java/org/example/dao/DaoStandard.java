package org.example.dao;

import io.vavr.control.Either;

import java.util.List;

public interface DaoStandard<T> {
    int add(T t);
    int update(T t);
    int delete(int id);

    Either<Integer, T> get(int id);

    Either<Integer, List<T>> getAll();
}
