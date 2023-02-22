package dao;

import domain.modelo.Article;
import domain.modelo.QueryArticlesAndNews;
import domain.modelo.QueryDescAndReaders;
import io.vavr.control.Either;

import java.util.List;

public interface ArticlesDAO {
    List<Article> getAll();

    Either<String, Article> get(int id);

    List<Article> getAllByType(String idType);

    QueryDescAndReaders getQuery(String id);

    int add(Article article);

    int update(Article article);

    int delete(List<Article> articlesList);


    List<Article> getAllByNewspaper(String idNews);

    List<QueryArticlesAndNews> getAllQuerySpring(String idType);


}
