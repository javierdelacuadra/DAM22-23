package serivces.impl;

import common.NumericConstants;
import dao.impl.ReadersDAOImpl;
import model.Newspaper;
import model.Reader;
import model.TypeArt;
import serivces.ReaderServices;
import serivces.common.ServicesConstants;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

public class ReaderServicesImpl implements ReaderServices {
    private final ReadersDAOImpl readersDAO;

    @Inject
    public ReaderServicesImpl(ReadersDAOImpl readersDAO) {
        this.readersDAO = readersDAO;
    }

    @Override
    public Either<String, List<Reader>> getAll() {
        Either<Integer, List<Reader>> listReaders = readersDAO.getAll();
        return getGetAllEither(listReaders);
    }

    @Override
    public Either<String, Reader> get(Reader reader) {
        Either<Integer, Reader> readerReturned = readersDAO.get(reader.getId());
        Either<String, Reader> either;
        if (readerReturned.isRight()) {
            either = Either.right(readerReturned.get());
        } else {
            switch (readerReturned.getLeft()) {
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                        either = Either.left(ServicesConstants.ERROR_GETTING_READER_FROM_THE_DB_WITH_INDEX + reader.getId() + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case NumericConstants.DB_EXCEPTION_CODE ->
                        either = Either.left(ServicesConstants.ERROR_GETTING_READER_FROM_THE_DB_WITH_INDEX + reader.getId());
                default ->
                        either = Either.left(ServicesConstants.ERROR_GETTING_READER_FROM_THE_DB_WITH_INDEX + reader.getId() + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
            }
        }
        return either;
    }


    // This method will get from DAO:
    // >0 -> ROWS DELETED (EXPECTED 1)
    // -1 -> error with the transaction, rollback done successfully
    // NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> error not related to de DB (Exception e)
    // NumericConstants.DB_EXCEPTION_CODE -> error with the transaction, rollback failed
    // -4 -> error with the transaction connection related to de DB (SQLException e)
    @Override
    public Either<String, String> delete(Reader readerSelected) {
        Either<String, String> either;
        int idReader = readerSelected.getId();
        int code = readersDAO.delete(idReader);
        if (code == 1) {
            either = Either.right(ServicesConstants.READER_DELETED + readerSelected.getName());
        } else {
            either = switch (code) {
                case -1 ->
                        Either.left(ServicesConstants.ERROR_DELETING_READER + readerSelected.getName() + ServicesConstants.BREAK + ServicesConstants.ERROR_ROLLBACK_DONE);
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                        Either.left(ServicesConstants.ERROR_DELETING_READER + readerSelected.getName() + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case NumericConstants.DB_EXCEPTION_CODE ->
                        Either.left(ServicesConstants.ERROR_DELETING_READER + readerSelected.getName() + ServicesConstants.BREAK + ServicesConstants.ERROR_ROLLBACK_FAILED);
                case -4 -> Either.left(ServicesConstants.ERROR_DELETING_READER + readerSelected.getName());
                default -> Either.left(ServicesConstants.UNKNOWN_ERROR + readerSelected.getName());
            };
        }
        return either;
    }


    //codes:
    //NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> error unconnected with the DB
    //-1 -> error adding to readers
    // 1 -> SUCCESS

    @Override
    public Either<String, Reader> add(Reader newReader) {
        Either<String, Reader> either;
        if (newReader.getName().equals("") || newReader.getBirthDate() == null || newReader.getLogin().getUser().equals("") || newReader.getLogin().getPassword().equals("")) {
            either = Either.left(ServicesConstants.PLEASE_FILL_ALL_THE_FIELDS);
        } else if (newReader.getBirthDate().isAfter(LocalDate.now().minusYears(5))) {
            either = Either.left(ServicesConstants.THE_NEW_READER_MUST_BE_AT_LEAST_5_YEARS_OLD);
        } else {
            int code = readersDAO.add(newReader);
            switch (code) {
                case NumericConstants.DB_EXCEPTION_CODE -> either = Either.left(ServicesConstants.USER_ALREADY_EXISTS);
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> either = Either.left(ServicesConstants.ERROR_ADDING_READER + newReader.getName()
                        + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case 1 -> either = Either.right(newReader);
                default -> either = Either.left(ServicesConstants.ERROR_ADDING_READER + newReader.getName());
            }
        }
        return either;
    }

    @Override
    public Either<String, Reader> update(Reader newReader) {
        Either<String, Reader> either;
        if (newReader.getName().equals("") || newReader.getBirthDate() == null
                || newReader.getLogin().getPassword().equals("")
                || newReader.getLogin().getUser().equals("")) {
            either = Either.left(ServicesConstants.PLEASE_FILL_ALL_THE_FIELDS);
        } else if (newReader.getBirthDate().isAfter(LocalDate.now().minusYears(5))) {
            either = Either.left(ServicesConstants.THE_NEW_READER_MUST_BE_AT_LEAST_5_YEARS_OLD);
        } else if (newReader.getLogin().getPassword().length() > 15
                || newReader.getLogin().getUser().length() > 15
                || newReader.getName().length() > 30) {
            either = Either.left(ServicesConstants.REMEMBER_THAT_THE_CRENTIALS_CAN_NOT_BE_LONGER_THAN_15_CHARACTERS_AND_THE_NAME_MUST_HAVE_LESS_THAN_30_AS_WELL);
        } else {
            int code = readersDAO.update(newReader);
            switch (code) {
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE -> either = Either.left(ServicesConstants.ERROR_UPDATING_READER + ServicesConstants.BREAK + newReader.getName()
                        + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case NumericConstants.DB_EXCEPTION_CODE, -1 -> either = Either.left(ServicesConstants.ERROR_UPDATING_READER + newReader.getName()
                        +  ServicesConstants.BREAK + ServicesConstants.ERROR_ROLLBACK_DONE);
                case -4 -> either = Either.left(ServicesConstants.ERROR_UPDATING_READER + newReader.getName()
                        +  ServicesConstants.BREAK + ServicesConstants.ERROR_ROLLBACK_FAILED);
                case NumericConstants.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION -> either = Either.left(ServicesConstants.ERROR_UPDATING_READER + newReader.getName()
                        + ServicesConstants.BREAK + ServicesConstants.USER_ALREADY_EXISTS);
                case 1 -> either = Either.right(newReader);
                default -> either = Either.left(ServicesConstants.ERROR_UPDATING_READER + newReader.getName() + ServicesConstants.BREAK + code);
            }
        }
        return either;
    }


    @Override
    public Either<String, List<Reader>> getReadersByType(TypeArt typeArt) {
        Either<Integer, List<Reader>> listReaders = readersDAO.getAll(typeArt);
        return getGetAllEither(listReaders);
    }

    @Override
    public Either<String, List<Reader>> getReadersByNewspaper(Newspaper newspaper) {
        Either<Integer, List<Reader>> listReaders = readersDAO.getAll(newspaper);
        Either<String, List<Reader>> returnEither = getGetAllEither(listReaders);
        if (returnEither.isLeft() && returnEither.getLeft().equals(ServicesConstants.THERE_ARE_NO_READERS_IN_THE_DB)) {
            returnEither = Either.left(returnEither.getLeft() + newspaper.getName_newspaper());
        }
        return returnEither;
    }

    @Override
    public Either<String, List<String>> getOldestReaders(Newspaper newspaperSelected) {
        Either<Integer, List<String>> listReaders = readersDAO.getAll(newspaperSelected, 5);
        Either<String, List<String>> returnEither;
        if (listReaders.isRight()) {
            if (listReaders.get().isEmpty()) {
                returnEither = Either.left(ServicesConstants.THERE_ARE_NO_READERS_IN_THE_DB);
            } else {
                returnEither = Either.right(listReaders.get());
            }
        } else {
            switch (listReaders.getLeft()) {
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                        returnEither = Either.left(ServicesConstants.ERROR_GETTING_READERS_FROM_THE_DB + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case NumericConstants.DB_EXCEPTION_CODE -> returnEither = Either.left(ServicesConstants.ERROR_GETTING_READERS_FROM_THE_DB);
                default ->
                        returnEither = Either.left(ServicesConstants.ERROR_GETTING_READERS_FROM_THE_DB + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
            }
        }
        if (returnEither.isLeft() && returnEither.getLeft().equals(ServicesConstants.THERE_ARE_NO_READERS_IN_THE_DB)) {
            returnEither = Either.left(returnEither.getLeft() + newspaperSelected.getName_newspaper());
        }
        return returnEither;
    }

    private Either<String, List<Reader>> getGetAllEither(Either<Integer, List<Reader>> listReaders) {
        Either<String, List<Reader>> either;
        if (listReaders.isRight()) {
            if (listReaders.get().isEmpty()) {
                either = Either.left(ServicesConstants.THERE_ARE_NO_READERS_IN_THE_DB);
            } else {
                either = Either.right(listReaders.get());
            }
        } else {
            switch (listReaders.getLeft()) {
                case NumericConstants.NON_RELATED_TO_DB_EXCEPTION_CODE ->
                        either = Either.left(ServicesConstants.ERROR_GETTING_READERS_FROM_THE_DB + ServicesConstants.BREAK + ServicesConstants.NON_RELATED_WITH_THE_DB);
                case NumericConstants.DB_EXCEPTION_CODE -> either = Either.left(ServicesConstants.ERROR_GETTING_READERS_FROM_THE_DB);
                default ->
                        either = Either.left(ServicesConstants.ERROR_GETTING_READERS_FROM_THE_DB + ServicesConstants.BREAK + ServicesConstants.UNKNOWN_ERROR);
            }
        }
        return either;
    }


}

