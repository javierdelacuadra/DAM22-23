package servicios;

import data.DaoArticles;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.Newspaper;
import model.Reader;

import java.util.List;

public class ServicesArticlesSQL {

    private final DaoArticles daoArticles;

    @Inject
    public ServicesArticlesSQL(DaoArticles daoArticles) {
        this.daoArticles = daoArticles;
    }

    public Either<Integer, List<Article>> getArticles() {
        return daoArticles.getAll();
    }

    public Either<Integer, List<Article>> getArticlesByNewspaper(String name) {
        return daoArticles.getAll(name);
    }

    public Integer addArticle(Article article, Newspaper newspaper) {
        return daoArticles.add(article, newspaper);
    }

    public Integer deleteArticle(Article article) {
        return daoArticles.delete(article);
    }

    public Integer deleteArticleFromNewspaper(String name) {
        return daoArticles.delete(name);
    }

    public Integer updateArticle(Article article, String oldName) {
        return daoArticles.update(article, oldName);
    }

//    public Either<Integer, List<Article>> getArticlesByType(String type) {
//        return daoArticles.getAll(type);
//    }

    public List<Article> getArticlesAndTypes() {
//        return daoArticles.getAllWithTypes();
        return null;
    }

    public Either<Integer, List<Article>> getArticlesByReaderID(Reader reader) {
        return daoArticles.getAll(reader);
    }
}
