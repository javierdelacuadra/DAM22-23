package dao;

import domain.modelo.QueryBadRatedArticles;
import domain.modelo.ReadArticle;
import io.vavr.control.Either;

import java.util.List;

public interface ReadArticlesDAO {

    List<ReadArticle> getAll();

    Either<Integer, ReadArticle> get(int id);

    int update(ReadArticle readArticle);

    int add(ReadArticle ra);

    int delete(int id);

    List<QueryBadRatedArticles> getAllDifficultSpringQuery(String idNewspaper);

    int getRatingOfReadArticleByReader(String idReader, String idArticle);

}
