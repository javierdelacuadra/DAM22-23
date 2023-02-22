package dao;

import io.vavr.control.Either;
import model.QueryBadRatedArticles;
import model.ReadArticle;

import java.util.List;

public interface ReadArticlesDAO {

    Either<Integer, List<ReadArticle>> getAll();

    Either<Integer, ReadArticle> get(int id);

    int update(ReadArticle readArticle);
    int add(ReadArticle ra);

    int delete(int id);

    Either<Integer, List<QueryBadRatedArticles>> getAllDifficultSpringQuery(int idReader, int idNewspaper);

    Either<Integer, Integer> get(int idArticle, int idReader);
}
