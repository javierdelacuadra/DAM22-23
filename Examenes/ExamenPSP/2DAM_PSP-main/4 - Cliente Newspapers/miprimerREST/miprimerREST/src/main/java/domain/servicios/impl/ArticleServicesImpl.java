package domain.servicios.impl;

import dao.common.DAOConstants;
import dao.impl.ArticlesDAOImpl;
import domain.modelo.Article;
import domain.modelo.QueryArticlesAndNews;
import domain.modelo.QueryDescAndReaders;
import domain.modelo.errores.BaseDeDatosException;
import domain.modelo.errores.InvalidFieldsException;
import domain.modelo.errores.NotFoundException;
import domain.servicios.ArticleServices;
import domain.servicios.common.ServicesConstants;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ArticleServicesImpl implements ArticleServices {


    private final ArticlesDAOImpl articlesDAOImpl;

    @Inject
    public ArticleServicesImpl(ArticlesDAOImpl articlesDAOImpl) {
        this.articlesDAOImpl = articlesDAOImpl;
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articlesDAOImpl.getAll();
        if (!articles.isEmpty()) {
            return articles;
        }
        throw new NotFoundException(DAOConstants.THERE_ARE_NO_ARTICLES_IN_THE_NEWSPAPER);
    }

    @Override
    public List<Article> getArticlesByType(String idType) {
        return articlesDAOImpl.getAllByType(idType);
    }


    public List<Article> getArticlesByNewspaper(String idNews) {
        return articlesDAOImpl.getAllByNewspaper(idNews);
    }


    @Override
    public Article addArticle(Article article) {
        if (!article.getName_article().equals("")) {
            int code = articlesDAOImpl.add(article);
            if (code == 1) {
                return article;
            }
            throw new BaseDeDatosException(ServicesConstants.HA_OCURRIDO_UN_ERROR_AL_ANADIR_EL_ARTICULO);
        }
        throw new InvalidFieldsException(ServicesConstants.THE_ARTICLE_MUST_HAVE_A_NAME);
    }

    @Override
    public int update(Article article) {
        return articlesDAOImpl.update(article);
    }

    @Override
    public Either<String, Article> get(int id) {
        return articlesDAOImpl.get(id);
    }

    public QueryDescAndReaders getQuery(String id) {
        return articlesDAOImpl.getQuery(id);
    }

    @Override
    public Either<String, String> delete(List<Article> articlesList) {
        int code = articlesDAOImpl.delete(articlesList);
        if (code == 0) {
            return Either.right(ServicesConstants.ARTICLES_DELETED);
        } else {
            return Either.left(ServicesConstants.ERROR_DELETING_ARTICLES);
        }
    }

    @Override
    public List<QueryArticlesAndNews> getAllQuerySpring(String idType) {
        return articlesDAOImpl.getAllQuerySpring(idType);
    }
}
