package servicios;

import data.DaoReadArticles;
import jakarta.inject.Inject;
import model.Article;
import model.ReadArticle;

public class ServicesReadArticles {

    private final DaoReadArticles daoReadArticles;

    @Inject
    public ServicesReadArticles(DaoReadArticles daoReadArticles) {
        this.daoReadArticles = daoReadArticles;
    }

    public int addRating(ReadArticle readArticle, Article article) {
        return daoReadArticles.save(readArticle, article);
    }

    public int updateRating(ReadArticle readArticle, Article article) {
        return daoReadArticles.update(readArticle, article);
    }

//    public Map<Double, String> getAvgRating(Integer idReader) {
//        return daoReadArticles.getAvgRating(idReader);
//    }
}
