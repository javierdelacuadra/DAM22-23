package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.miutils.domain.modelo.TypeArt;

import java.util.List;

public interface TypeDAO {
    Single<Either<String, List<TypeArt>>> getAll();
}
