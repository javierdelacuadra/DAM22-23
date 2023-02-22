package serivces;

import model.Newspaper;
import model.Reader;
import io.vavr.control.Either;
import model.TypeArt;

import java.util.List;

public interface ReaderServices {

    Either<String, List<Reader>> getAll();

    Either<String, Reader> get(Reader reader);

    Either<String, Reader> add(Reader newReader);

    Either<String, Reader> update(Reader newReader);

    Either<String, String> delete(Reader readerSelected);


    // OTHER METHODS

    Either<String, List<Reader>> getReadersByType(TypeArt typeArt);

    Either<String, List<Reader>> getReadersByNewspaper(Newspaper newspaper);

    Either<String, List<String>> getOldestReaders(Newspaper newspaperSelected);
}
