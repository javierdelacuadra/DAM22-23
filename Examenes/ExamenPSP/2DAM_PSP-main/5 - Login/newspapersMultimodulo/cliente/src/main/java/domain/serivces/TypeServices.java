package domain.serivces;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.miutils.domain.modelo.TypeArt;

import java.util.List;

public interface TypeServices {
    Single<Either<String, List<TypeArt>>> getAllTypes();
}
