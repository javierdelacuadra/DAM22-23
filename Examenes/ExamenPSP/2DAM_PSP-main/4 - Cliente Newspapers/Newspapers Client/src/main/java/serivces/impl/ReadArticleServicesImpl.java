package serivces.impl;

import dao.impl.ReadArticlesDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.ReadArticle;
import serivces.ReadArticleServices;
import serivces.common.ServicesConstants;

public class ReadArticleServicesImpl implements ReadArticleServices {

    ReadArticlesDAOImpl readArticlesDAO;

    @Inject
    public ReadArticleServicesImpl(ReadArticlesDAOImpl readArticlesDAO) {
        this.readArticlesDAO = readArticlesDAO;
    }

    @Override
    public Either<String, String> add(ReadArticle ra) {
        int code = readArticlesDAO.add(ra);
        return switch (code) {
            case -2 -> Either.left(ServicesConstants.ERROR_ADDING_READARTICLE + ServicesConstants.NON_RELATED_WITH_THE_DB);
            case -3 -> Either.left(ServicesConstants.ERROR_ADDING_READARTICLE);
            case 1062 -> Either.left(ServicesConstants.ERROR_ADDING_READARTICLE + ra.getIdArticle()
                    + ServicesConstants.BREAK + ServicesConstants.THE_USER_ALREADY_READ_THE_ARTICLE);
            default -> Either.right(ServicesConstants.HAS_NOW_READ_THE_ARTICLE_N + ra.getIdArticle() + ServicesConstants.WITH_A_RATING_OF + ra.getRating());
        };


    }
}
