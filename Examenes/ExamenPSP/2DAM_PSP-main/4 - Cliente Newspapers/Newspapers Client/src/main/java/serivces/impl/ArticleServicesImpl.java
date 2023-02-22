package serivces.impl;

import dao.impl.ArticlesDAOImpl;
import model.*;
import serivces.common.ServicesConstants;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import serivces.ArticleServices;

import java.util.List;

public class ArticleServicesImpl implements ArticleServices {


    private ArticlesDAOImpl articlesDAOImpl;

    @Inject
    public ArticleServicesImpl(ArticlesDAOImpl articlesDAOImpl) {
        this.articlesDAOImpl = articlesDAOImpl;
    }

    @Override
    public Either<String, List<Article>> getAllArticles() {
       return readEitherListArticles(articlesDAOImpl.getAll(), ServicesConstants.THERE_ARE_NO_ARTICLES_IN_THE_DB);
    }

    @Override
    public Either<String, List<Article>> getArticlesByType(TypeArt typeArt) { // POR ACA
        Either<Integer, List<Article>> either = articlesDAOImpl.getAll(typeArt.getId());
        return readEitherListArticles(either, ServicesConstants.THERE_ARE_NO_ARTICLES_OF_THIS_TYPE);
    }

    private static Either<String, List<Article>> readEitherListArticles(Either<Integer, List<Article>> either, String emptyRightMessage) {
        if (either.isLeft()) {
            return switch (either.getLeft()) {
                case -2 ->
                        Either.left(ServicesConstants.ERROR_GETTING_ALL_ARTICLES + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case -3 -> Either.left(ServicesConstants.ERROR_GETTING_ALL_ARTICLES);
                default ->
                        Either.left(ServicesConstants.ERROR_GETTING_ALL_ARTICLES + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
            };
        } else if (either.get().isEmpty()) {
            return Either.left(emptyRightMessage);
        } else {
            return Either.right(either.get());
        }
    }


    public Either<String, List<Article>> getArticlesByNewspaper(Newspaper newspaper) {
        Either<Integer, List<Article>> articlesFiltered = articlesDAOImpl.getAll();
        return readEitherListArticles(articlesFiltered, ServicesConstants.THERE_ARE_NO_ARTICLES_IN_THE_DB);
    }

    @Override
    public int getNewIndex() {
        Either<Integer, List<Article>> list = articlesDAOImpl.getAll();
        if (list.isRight()) {
            return list.get().stream().mapToInt(Article::getId).max().orElse(0) + 1;
        } else {
            return -1;
        }
    }

    @Override
    public Either<String, List<Article>> getUnreadArtsByReaderAndNews(Reader currentReader, Newspaper newspaper) {
        Either<Integer, List<Article>> either = articlesDAOImpl.getAll(currentReader, newspaper);
        if (either.isLeft()){
            switch (either.getLeft()){
                case -2 -> {
                    return Either.left(ServicesConstants.ERROR_GETTING_UNREAD_ARTICLES + ServicesConstants.NON_RELATED_WITH_THE_DB);
                }
                case -3 -> {
                    return Either.left(ServicesConstants.ERROR_GETTING_UNREAD_ARTICLES);
                }
                default -> {
                    return Either.left(ServicesConstants.ERROR_GETTING_UNREAD_ARTICLES + ServicesConstants.UNKNOWN_ERROR);
                }
            }
        }
        else if (!either.get().isEmpty()){
            return Either.right(either.get());
        }
        else {
            return Either.left(currentReader.getName() + ServicesConstants.HAS_READ_EVERY_ARTICLE_OF + newspaper.getName_newspaper());
        }
    }


    @Override
    public Either<String, String> addArticle(Article article) {
        int code = articlesDAOImpl.add(article);
        Either<String, String> either;
        switch (code) {
            case 1 -> either = Either.right(ServicesConstants.ARTICLE_ADDED_SUCCESSFULLY);
            case 1062 -> either = Either.left(ServicesConstants.ARTICLE_ALREADY_EXISTS);
            case -3 -> either = Either.left(ServicesConstants.ERROR_WHILE_ADDING_THE_ARTICLE);
            case -2 ->
                    either = Either.left(ServicesConstants.ERROR_WHILE_ADDING_THE_ARTICLE + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
            default -> either = Either.left(ServicesConstants.ERROR_WHILE_ADDING_THE_ARTICLE + ServicesConstants.BREAK  + ServicesConstants.UNKNOWN_ERROR);
        }
        return either;
    }

    @Override
    public int update(Article article) {
        return articlesDAOImpl.update(article);
    }

    @Override
    public Either<String, Article> get(int id) {
        return articlesDAOImpl.get(id);
    }

    public Either<String, QueryDescAndReaders> getQuery(int id) {
        Either<Integer, QueryDescAndReaders> either = articlesDAOImpl.getQuery(id);
        if (either.isLeft()) {
            switch (either.getLeft()) {
                case -2 -> {
                    return Either.left(ServicesConstants.ERROR_GETTING_THE_DESCRIPTION_AND_NUMBER_OF_READERS_OF_THE_ARTICLE +
                            id + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                }
                case -3 -> {
                    return Either.left(ServicesConstants.ERROR_GETTING_THE_DESCRIPTION_AND_NUMBER_OF_READERS_OF_THE_ARTICLE +
                            id);
                }
                default -> {
                    return Either.left(ServicesConstants.UNKNOWN_ERROR);
                }
            }
        } else if (either.get() == null){
            return Either.left(ServicesConstants.THERE_IS_NO_ARTICLE_WITH_ID + id);
        }
        return Either.right(either.get());
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
}
