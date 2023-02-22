package serivces;

import io.vavr.control.Either;
import model.*;

import java.util.List;

public interface ArticleServices {
    Either<String, List<Article>> getAllArticles();


    Either<String, Article> get(int id);

    Either<String, QueryDescAndReaders> getQuery(int id);

    Either<String, String> addArticle(Article article);

    int update(Article article);

    Either<String, String> delete(List<Article> articlesList);

    Either<String, List<Article>> getArticlesByType(TypeArt typeArt);

    Either<String, List<Article>> getArticlesByNewspaper(Newspaper newspaper);

    Either<String, List<QueryArticlesAndNews>> getAllQuerySpring(int idType);
}
