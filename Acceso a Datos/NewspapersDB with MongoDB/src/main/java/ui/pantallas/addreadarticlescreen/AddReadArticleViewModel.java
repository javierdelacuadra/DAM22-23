package ui.pantallas.addreadarticlescreen;

import jakarta.inject.Inject;
import model.ReadArticle;
import servicios.ServicesArticlesSQL;
import servicios.ServicesReadArticles;

public class AddReadArticleViewModel {

    private final ServicesArticlesSQL servicesArticlesSQL;
    private final ServicesReadArticles servicesReadArticles;

    @Inject
    public AddReadArticleViewModel(ServicesArticlesSQL servicesArticlesSQL, ServicesReadArticles servicesReadArticles) {
        this.servicesArticlesSQL = servicesArticlesSQL;
        this.servicesReadArticles = servicesReadArticles;
    }

//    public Either<Integer, List<Article>> getArticles(Reader reader) {
//        return servicesArticlesSQL.getArticlesByReaderID(reader);
//    }

    public int addRating(ReadArticle readArticle) {
        return servicesReadArticles.addRating(readArticle);
    }

    public int updateRating(ReadArticle readArticle) {
        return servicesReadArticles.updateRating(readArticle);
    }
}