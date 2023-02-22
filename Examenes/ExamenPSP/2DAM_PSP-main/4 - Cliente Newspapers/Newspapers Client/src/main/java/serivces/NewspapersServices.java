package serivces;

import io.vavr.control.Either;
import model.Newspaper;
import model.Reader;

import java.nio.channels.NonWritableChannelException;
import java.util.List;

public interface NewspapersServices {
    Either<String, List<Newspaper>> getAll();

    Either<String, List<Newspaper>> getAll(Reader reader);


    Either<String, Newspaper> get(Newspaper news);

    Either<String, String> add (Newspaper newspaper);

    Either<String, Newspaper> update(Newspaper news);
    Either<String, String> delete(Newspaper newspaperSelected);

    Either<String, List<Newspaper>> getNewspaperByIDs(List<Integer> integers);
}
