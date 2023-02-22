package serivces.impl;

import dao.impl.NewspapersDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;
import model.Reader;
import serivces.NewspapersServices;
import serivces.common.ServicesConstants;

import java.time.LocalDate;
import java.util.List;

public class NewspapersServicesImpl implements NewspapersServices {

    public static final String THERE_ARE_NO_NEWSPAPERS_IN_THE_DATABASE = "There are no newspapers in the database";
    public static final String THERE_WAS_AN_ERROR_LOADING_THE_NEWSPAPERS = "There was an error loading the newspapers";
    private NewspapersDAOImpl newspapersDAOImpl;

    @Inject
    public NewspapersServicesImpl(NewspapersDAOImpl newspapersDAOImpl) {
        this.newspapersDAOImpl = newspapersDAOImpl;
    }

    @Override
    public Either<String, List<Newspaper>> getAll() {
        Either<Integer, List<Newspaper>> either = newspapersDAOImpl.getAll();
        Either<String, List<Newspaper>> result;
        if (either.isRight() && either.get().isEmpty()) {
            result = Either.left(THERE_ARE_NO_NEWSPAPERS_IN_THE_DATABASE);
        } else if (either.isLeft()) {
            result = switch (either.getLeft()) {
                case -2 -> Either.left(THERE_WAS_AN_ERROR_LOADING_THE_NEWSPAPERS + ServicesConstants.BREAK
                        + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case -3 -> Either.left(THERE_WAS_AN_ERROR_LOADING_THE_NEWSPAPERS);
                default ->
                        Either.left(THERE_WAS_AN_ERROR_LOADING_THE_NEWSPAPERS + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
            };
        } else {
            result = Either.right(either.get());
        }
        return result;
    }

    @Override
    public Either<String, List<Newspaper>> getAll(Reader reader) {
        Either<Integer, List<Newspaper>> either = newspapersDAOImpl.getAll(reader);
        if (either.isLeft()) {
            switch (either.getLeft()) {
                case -2 -> {
                    return Either.left(ServicesConstants.ERROR_GETTING_ALL_SUBSCRIPTIONS + ServicesConstants.NON_RELATED_WITH_THE_DB);
                }
                case -3 -> {
                    return Either.left(ServicesConstants.ERROR_GETTING_ALL_SUBSCRIPTIONS);
                }
                default -> {
                    return Either.left(ServicesConstants.ERROR_GETTING_ALL_SUBSCRIPTIONS + ServicesConstants.UNKNOWN_ERROR);
                }
            }
        }
        return Either.right(either.get());
    }

    @Override
    public Either<String, Newspaper> get(Newspaper news) {
        Either<String, Newspaper> response = newspapersDAOImpl.get(news);
        if (response.isRight() && response.get().getId() != -1) {
            return Either.right(response.get());
        } else {
            return Either.left(ServicesConstants.THE_NEWSPAPER_DOES_NOT_EXIST);
        }
    }

    @Override
    public Either<String, String> add(Newspaper newspaper) {
        if (newspaper.getName_newspaper().equals("") || newspaper.getRelease_date() == null) {
            return Either.left(ServicesConstants.PLEASE_FILL_ALL_THE_FIELDS);
        } else if (newspaper.getRelease_date().isAfter(LocalDate.now())) {
            return Either.left(ServicesConstants.THE_RELEASE_DATE_CANNOT_BE_IN_THE_FUTURE);
        } else {
            return switch (newspapersDAOImpl.add(newspaper)) {
                case -2 ->
                        Either.left(ServicesConstants.ERROR_ADDING_NEWSPAPER + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case 1 ->
                        Either.right(ServicesConstants.NEWSPAPER + newspaper.getName_newspaper() + ServicesConstants.ADDED_SUCCESSFULLY);
                case -3 ->  Either.left(ServicesConstants.ERROR_ADDING_NEWSPAPER);
                case 1062 -> Either.left(ServicesConstants.ERROR_ADDING_NEWSPAPER + ServicesConstants.BREAK + ServicesConstants.THE_NEWSPAPER_ALREADY_EXISTS);
                default ->
                        Either.left(ServicesConstants.ERROR_ADDING_NEWSPAPER + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
            };
        }
    }

    @Override
    public Either<String, Newspaper> update(Newspaper news) {
        Either<String, Newspaper> result;
        String invalidNews = invalidNewsMessage(news);
        if (invalidNews == null) {
            result = switch (newspapersDAOImpl.update(news)) {
                case -2 ->
                        Either.left(ServicesConstants.ERROR_UPDATING_NEWSPAPER + news.getName_newspaper() + ServicesConstants.BREAK
                                + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case -3 -> Either.left(ServicesConstants.ERROR_UPDATING_NEWSPAPER + news.getName_newspaper());
                case 1062 ->
                        Either.left(ServicesConstants.ERROR_UPDATING_NEWSPAPER + news.getName_newspaper() + ServicesConstants.BREAK
                                + ServicesConstants.NEWS_ALREADY_EXISTS);
                case 1 -> Either.right(news);
                default -> Either.left(ServicesConstants.ERROR_UPDATING_NEWSPAPER + news.getName_newspaper() +
                        ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
            };
        } else {
            result = Either.left(invalidNews);
        }
        return result;
    }

    private static String invalidNewsMessage(Newspaper news) {
        if (news.getName_newspaper().equals("") || news.getRelease_date() == null) {
            return ServicesConstants.PLEASE_FILL_ALL_THE_FIELDS;
        } else if (news.getRelease_date().isAfter(LocalDate.now())) {
            return ServicesConstants.THE_RELEASE_DATE_CANNOT_BE_IN_THE_FUTURE;
        }
        return null;
    }

    @Override
    public Either<String, String> delete(Newspaper newspaperSelected) {
        int code = newspapersDAOImpl.delete(newspaperSelected.getId());
        return switch (code) {
            case 1 -> Either.right(ServicesConstants.NEWSPAPER_DELETED + newspaperSelected.getName_newspaper());
            case -3 -> Either.left(ServicesConstants.ERROR_DELETING_NEWSPAPER + newspaperSelected.getName_newspaper());
            case -2 ->
                    Either.left(ServicesConstants.ERROR_DELETING_NEWSPAPER + newspaperSelected.getName_newspaper() + ServicesConstants.BREAK
                            + ServicesConstants.NON_RELATED_WITH_THE_DB);
            default ->
                    Either.left(ServicesConstants.ERROR_DELETING_NEWSPAPER + newspaperSelected.getName_newspaper() + ServicesConstants.BREAK
                            + ServicesConstants.UNKNOWN_ERROR);
        };
    }

    @Override
    public Either<String, List<Newspaper>> getNewspaperByIDs(List<Integer> ids) {
        Either<String, List<Newspaper>> either;
        try {
            either = Either.right(getAll().get().stream().filter(newspaper -> ids.contains(newspaper.getId())).toList());
            if (either.isRight() && either.get() == null || either.get().isEmpty()) {
                either = Either.left(ServicesConstants.HAS_READ_EVERY_ARTICLE_IN_THE_NEWSPAPERS_THEY_ARE_SUBSCRIBED_TO);
            }
        } catch (Exception e) {
            either = Either.left(ServicesConstants.ERROR_GETTING_NEWSPAPERS_WITH_IDS + ids + ServicesConstants.BREAK + e.getMessage());
        }
        return either;
    }
}
