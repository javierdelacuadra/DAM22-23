package servicios;

import data.DaoReadArticles;
import jakarta.inject.Inject;
import model.ReadArticle;

public class ServicesReadArticles {

    private final DaoReadArticles daoReadArticles;

    @Inject
    public ServicesReadArticles(DaoReadArticles daoReadArticles) {
        this.daoReadArticles = daoReadArticles;
    }

    public int addRating(ReadArticle readArticle) {
        return daoReadArticles.saveReadArticle(readArticle);
    }
}
