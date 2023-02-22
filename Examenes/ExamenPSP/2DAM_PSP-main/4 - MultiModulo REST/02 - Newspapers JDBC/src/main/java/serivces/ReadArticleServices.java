package serivces;

import io.vavr.control.Either;
import model.QueryBadRatedArticles;
import model.ReadArticle;

import java.util.List;

public interface ReadArticleServices {

    Either<String, String> add(ReadArticle ra);

    Either<String, List<QueryBadRatedArticles>> getAllDifficultSpringQuery(int idReader, int idNewspaper);

    Either<Integer, Integer> getRating(int id, int id1);
}
