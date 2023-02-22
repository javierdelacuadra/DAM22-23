package dao;

import io.vavr.control.Either;
import model.*;

import java.util.List;

public interface ArticlesDAO {
    Either<Integer, List<Article>> getAll();

    Either<String, Article> get(int id);

    Either<Integer, List<Article>> getAll(int idType);

    Either<Integer, QueryDescAndReaders> getQuery(int id);

    int add(Article article);

    int update(Article article);
    int delete(List<Article> articlesList);



    Either<Integer, List<Article>> getAll(Newspaper newspaper);

    Either<Integer, List<QueryArticlesAndNews>> getAllQuerySpring(int idType);
}
