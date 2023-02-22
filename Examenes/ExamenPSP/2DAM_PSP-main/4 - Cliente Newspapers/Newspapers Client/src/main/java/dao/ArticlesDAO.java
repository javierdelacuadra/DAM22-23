package dao;

import io.vavr.control.Either;
import model.Article;
import model.Newspaper;
import model.QueryDescAndReaders;
import model.Reader;

import java.util.List;

public interface ArticlesDAO {
    Either<Integer, List<Article>> getAll();

    Either<String, Article> get(int id);

    Either<Integer, List<Article>> getAll(int idType);

    Either<Integer, QueryDescAndReaders> getQuery(int id);

    int add(Article article);

    int update(Article article);
    int delete(List<Article> articlesList);


    Either<Integer, List<Article>> getAll(Reader currentReader, Newspaper newspaper);

}
