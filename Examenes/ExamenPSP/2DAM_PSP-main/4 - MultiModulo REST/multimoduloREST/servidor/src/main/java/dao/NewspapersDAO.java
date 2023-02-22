package dao;

import org.miutils.domain.modelo.Newspaper;

import java.util.List;

public interface NewspapersDAO {
    List<Newspaper> getAll();

    Newspaper get(String idNews);

    int add(Newspaper newspaper);

    int update(Newspaper news);

    int delete(String idNews);
}
