package domain.serivces;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;
import retrofit2.Response;

import java.util.List;

public interface ReaderServices {

    Single<Either<String, List<Reader>>> getAll();

    Single<Either<String, Reader>> get(Reader reader);

    Single<Either<String, Reader>> add(Reader newReader);

    Single<Either<String, Reader>> update(Reader newReader);

    Single<Either<String, Response<Object>>> delete(Reader readerSelected);


    // OTHER METHODS

    Single<Either<String, List<Reader>>> getReadersByType(TypeArt typeArt);

    Single<Either<String, List<Reader>>> getReadersByNewspaper(Newspaper newspaper);

    Single<Either<String, List<String>>> getOldestReaders(Newspaper newspaperSelected);
}
