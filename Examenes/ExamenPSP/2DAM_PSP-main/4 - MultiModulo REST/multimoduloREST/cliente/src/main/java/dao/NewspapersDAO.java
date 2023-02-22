package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.miutils.domain.modelo.Newspaper;
import retrofit2.Response;

import java.util.List;

public interface NewspapersDAO {
    Single<Either<String, List<Newspaper>>> getAll();

    Single<Either<String, Newspaper>> add(Newspaper newspaper);

    Single<Either<String, Newspaper>> update(Newspaper news);

    Single<Either<String, Response<Object>>> delete(int id);

}
