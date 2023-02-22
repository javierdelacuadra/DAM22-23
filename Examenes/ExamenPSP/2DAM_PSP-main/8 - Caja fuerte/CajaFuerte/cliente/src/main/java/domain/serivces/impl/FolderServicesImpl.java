package domain.serivces.impl;

import dao.impl.FoldersDaoImpl;
import domain.serivces.FoldersServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.utils.domain.modelo.Folder;
import retrofit2.Response;

import java.util.List;

public class FolderServicesImpl implements FoldersServices {

    FoldersDaoImpl foldersDao;

    @Inject
    public FolderServicesImpl(FoldersDaoImpl foldersDao) {
        this.foldersDao = foldersDao;
    }

    @Override
    public Single<Either<String, Folder>> get(String nameUser, String nameFolder, String passFolder) {
        return foldersDao.get(nameUser, nameFolder, passFolder);
    }

    @Override
    public Single<Either<String, Response<Object>>> add(Folder f) {
        return foldersDao.add(f);
    }

    @Override
    public Single<Either<String, List<Folder>>> getAll(String nameUser) {
        return foldersDao.getAll(nameUser);
    }

    @Override
    public Single<Either<String, Folder>> changePass(String userName, String folderName, String oldPass, String newPass) {
        return foldersDao.changePass(userName, folderName, oldPass, newPass);
    }
}
