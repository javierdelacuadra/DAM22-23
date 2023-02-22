package serivces.impl;

import common.NumericConstants;
import dao.impl.ReadArticlesDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.QueryBadRatedArticles;
import model.ReadArticle;
import serivces.ReadArticleServices;
import serivces.common.ServicesConstants;

import java.util.List;

public class ReadArticleServicesImpl implements ReadArticleServices {

    ReadArticlesDAOImpl readArticlesDAO;

    @Inject
    public ReadArticleServicesImpl(ReadArticlesDAOImpl readArticlesDAO) {
        this.readArticlesDAO = readArticlesDAO;
    }

    @Override
    public Either<String, String> add(ReadArticle ra) {
        int code = readArticlesDAO.add(ra);
        if (code == NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION) {
            code = readArticlesDAO.update(ra);
        }
        return switch (code) {
            case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> Either.left(ServicesConstants.ERROR_ADDING_READARTICLE + ServicesConstants.NON_RELATED_WITH_THE_DB);
            case NumericConstants.DB_EXCEPTION_CODE -> Either.left(ServicesConstants.ERROR_ADDING_READARTICLE);
            case NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION -> Either.left(ServicesConstants.ERROR_ADDING_READARTICLE + ra.getIdArticle()
                    + ServicesConstants.BREAK + ServicesConstants.THE_USER_ALREADY_READ_THE_ARTICLE);
            default -> Either.right(ServicesConstants.YOU_HAVE_NOW_READ_THE_ARTICLE_N + ra.getIdArticle() + ServicesConstants.WITH_A_RATING_OF + ra.getRating());
        };
    }

    @Override
    public Either<String, List<QueryBadRatedArticles>> getAllDifficultSpringQuery(int idReader, int idNewspaper) {
        Either<Integer, List<QueryBadRatedArticles>> either = readArticlesDAO.getAllDifficultSpringQuery(idReader, idNewspaper);
        Either<String, List<QueryBadRatedArticles>> result;
        if (either.isLeft()){
            switch (either.getLeft()){
                case NumericConstants.DB_EXCEPTION_CODE -> result = Either.left(ServicesConstants.ERROR_GETTING_THE_ARTICLES_RATED_WITH_HIGHER_THAN_A_FIVE_IF_YOU_RATED_MORE_THAN_2);
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> result = Either.left(ServicesConstants.ERROR_GETTING_THE_ARTICLES_RATED_WITH_HIGHER_THAN_A_FIVE_IF_YOU_RATED_MORE_THAN_2 + ServicesConstants.NON_RELATED_WITH_THE_DB +
                    ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                default -> result = Either.left(ServicesConstants.ERROR_GETTING_THE_ARTICLES_RATED_WITH_HIGHER_THAN_A_FIVE_IF_YOU_RATED_MORE_THAN_2 + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR + either.getLeft());
            }
        } else {
            result = Either.right(either.get());
        }
        return result;
    }

    @Override
    public Either<Integer, Integer> getRating(int idArticle, int idReader) {
        return readArticlesDAO.get(idArticle, idReader);
    }
}
