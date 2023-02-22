package serivces.impl;

import common.NumericConstants;
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
            result = Either.left(ServicesConstants.THERE_ARE_NO_NEWSPAPERS_IN_THE_DATABASE);
        } else if (either.isLeft()) {
            result = switch (either.getLeft()) {
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> Either.left(ServicesConstants.THERE_WAS_AN_ERROR_LOADING_THE_NEWSPAPERS + ServicesConstants.BREAK
                        + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case NumericConstants.DB_EXCEPTION_CODE -> Either.left(ServicesConstants.THERE_WAS_AN_ERROR_LOADING_THE_NEWSPAPERS);
                default ->
                        Either.left(ServicesConstants.THERE_WAS_AN_ERROR_LOADING_THE_NEWSPAPERS + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
            };
        } else {
            result = Either.right(either.get());
        }
        return result;
    }

    @Override
    public Either<String, List<Newspaper>> getAll(Reader reader) {
        Either<Integer, List<Newspaper>> either = newspapersDAOImpl.getAll(reader);
        return getStringListEither(either, ServicesConstants.ERROR_GETTING_ALL_SUBSCRIPTIONS, ServicesConstants.YOU_ARE_NOT_SUBSCRIBED_TO_ANY_NEWSPAPER);
    }

    private  Either<String, List<Newspaper>> getStringListEither(Either<Integer, List<Newspaper>> either, String defaultMsg, String emptyListMsg) {
        if (either.isLeft()) {
            switch (either.getLeft()) {
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> {
                    return Either.left(defaultMsg + ServicesConstants.NON_RELATED_WITH_THE_DB);
                }
                case NumericConstants.DB_EXCEPTION_CODE -> {
                    return Either.left(defaultMsg);
                }
                default -> {
                    return Either.left(defaultMsg + ServicesConstants.UNKNOWN_ERROR);
                }
            }
        } else if (either.isRight() && either.get().isEmpty()) {
            return Either.left(emptyListMsg);
        }
        return Either.right(either.get());
    }

    @Override
    public Either<String, List<Newspaper>> getAll(int idReader, boolean suscribed) {
        Either<Integer, List<Newspaper>> either = newspapersDAOImpl.getAll(idReader, suscribed);
        if (suscribed){
            return getStringListEither(either, ServicesConstants.ERROR_GETTING_NEWSPAPERS_SUSCRIBED, ServicesConstants.YOU_ARE_NOT_SUBSCRIBED_TO_ANY_NEWSPAPER);
        } else {
            return getStringListEither(either, ServicesConstants.ERROR_GETTING_NEWSPAPERS_UNSUSCRIBED, ServicesConstants.YOU_ARE_SUBSCRIBED_TO_EVERY_NEWSPAPER);
        }
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
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                        Either.left(ServicesConstants.ERROR_ADDING_NEWSPAPER + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case 1 ->
                        Either.right(ServicesConstants.NEWSPAPER + newspaper.getName_newspaper() + ServicesConstants.ADDED_SUCCESSFULLY);
                case NumericConstants.DB_EXCEPTION_CODE ->  Either.left(ServicesConstants.ERROR_ADDING_NEWSPAPER);
                case NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION -> Either.left(ServicesConstants.ERROR_ADDING_NEWSPAPER + ServicesConstants.BREAK + ServicesConstants.THE_NEWSPAPER_ALREADY_EXISTS);
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
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                        Either.left(ServicesConstants.ERROR_UPDATING_NEWSPAPER + news.getName_newspaper() + ServicesConstants.BREAK
                                + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case NumericConstants.DB_EXCEPTION_CODE -> Either.left(ServicesConstants.ERROR_UPDATING_NEWSPAPER + news.getName_newspaper());
                case NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION ->
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
    public Either<String, String> delete(Newspaper newspaperSelected, boolean confirmed) {
        int code = newspapersDAOImpl.delete(newspaperSelected.getId(), confirmed);
        return switch (code) {
            case 1 -> Either.right(ServicesConstants.NEWSPAPER_DELETED + newspaperSelected.getName_newspaper());
            case NumericConstants.DATA_INTEGRITY_VIOLATION_EXCEPTION -> Either.left(ServicesConstants.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_NEWSPAPER_WITH_ALL_ITS_ARTICLES);
            case NumericConstants.DB_EXCEPTION_CODE -> Either.left(ServicesConstants.ERROR_DELETING_NEWSPAPER + newspaperSelected.getName_newspaper());
            case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                    Either.left(ServicesConstants.ERROR_DELETING_NEWSPAPER + newspaperSelected.getName_newspaper() + ServicesConstants.BREAK
                            + ServicesConstants.NON_RELATED_WITH_THE_DB);
            default ->
                    Either.left(ServicesConstants.ERROR_DELETING_NEWSPAPER + newspaperSelected.getName_newspaper() + ServicesConstants.BREAK
                            + ServicesConstants.UNKNOWN_ERROR);
        };
    }
}
