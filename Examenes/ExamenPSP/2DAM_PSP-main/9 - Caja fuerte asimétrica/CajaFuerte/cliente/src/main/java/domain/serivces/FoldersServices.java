package domain.serivces;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.utils.domain.modelo.Folder;
import retrofit2.Response;

import java.util.List;

public interface FoldersServices {

    Single<Either<String, Folder>> get(String nameUser, String nameFolder, String passFolder);

    Single<Either<String, Response<Object>>> add(Folder f);

    Single<Either<String, List<Folder>>> getAll(String nameUser);

}
