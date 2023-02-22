package domain.servicios;

import domain.modelo.Article;
import domain.modelo.QueryArticlesAndNews;
import domain.modelo.QueryDescAndReaders;
import io.vavr.control.Either;

import java.util.List;

public interface ArticleServices {
    List<Article> getAllArticles();


    Either<String, Article> get(int id);

    QueryDescAndReaders getQuery(String id);

    Article addArticle(Article article);

    int update(Article article);

    Either<String, String> delete(List<Article> articlesList);

    List<Article> getArticlesByType(String idType);

    List<Article> getArticlesByNewspaper(String idNewspaper);

    List<QueryArticlesAndNews> getAllQuerySpring(String idType);
}
