package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.utils.domain.modelo.Folder;
import retrofit2.Response;

import java.util.List;

public interface FoldersDao {

    Single<Either<String, Folder>> get(String nameUser, String nameFolder);

    Single<Either<String, Response<Object>>> add(Folder f);

    Single<Either<String, List<Folder>>> getAll(String nameUser);

    Single<Either<String, Folder>> changePass(String userName, String folderName, String oldPass, String newPass);
}
