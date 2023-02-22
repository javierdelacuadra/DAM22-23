package domain.servicios;

import domain.modelo.QueryBadRatedArticles;
import domain.modelo.ReadArticle;

import java.util.List;

public interface ReadArticleServices {


    ReadArticle add(ReadArticle ra);

    ReadArticle update(ReadArticle ra);

    List<QueryBadRatedArticles> getAllDifficultSpringQuery(String idNewspaper);

    int getRating(String idReader, String idArt);

    List<ReadArticle> getAll();
}
