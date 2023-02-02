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
        return daoReadArticles.save(readArticle);
    }

    public int updateRating(ReadArticle readArticle) {
        return daoReadArticles.update(readArticle);
    }

//    public Map<Double, String> getAvgRating(Integer idReader) {
//        return daoReadArticles.getAvgRating(idReader);
//    }
}
