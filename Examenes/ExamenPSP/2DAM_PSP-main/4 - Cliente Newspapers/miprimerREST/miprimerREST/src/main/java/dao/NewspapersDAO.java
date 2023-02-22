package dao;

import domain.modelo.Newspaper;

import java.util.List;

public interface NewspapersDAO {
    List<Newspaper> getAll();

    List<Newspaper> getAllSuscribed(String idReader);

    List<Newspaper> getAllUnsuscribed(String idReader);

    Newspaper get(String idNews);

    int add(Newspaper newspaper);

    int update(Newspaper news);

    int delete(String id);
}
