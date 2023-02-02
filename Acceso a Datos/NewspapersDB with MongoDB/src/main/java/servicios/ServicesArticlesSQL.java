package servicios;

import data.DaoArticles;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;

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

//    public Either<Integer, List<Article>> getArticlesByReaderID(Reader reader) {
//        return daoArticles.getAll(reader.getId());
//    }

    public Integer addArticle(Article article) {
        return daoArticles.add(article.getName(), article);
    }

    public Integer deleteArticle(Article article) {
        return daoArticles.delete(article);
    }

//    public Integer deleteArticleFromNewspaper(Integer id) {
//        return daoArticles.delete(id);
//    }

    public Integer updateArticle(Article article) {
        return daoArticles.update(article);
    }

//    public Either<Integer, List<Article>> getArticlesByType(String type) {
//        return daoArticles.getAll(type);
//    }

    public List<Article> getArticlesAndTypes() {
//        return daoArticles.getAllWithTypes();
        return null;
    }
}
