package dao.impl;


import common.CachedCredentials;
import dao.FoldersDao;
import dao.retrofit.llamadas.FoldersApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.seguridad.impl.EncriptacionAES;
import org.utils.domain.modelo.Folder;
import retrofit2.Response;

import java.util.List;

public class FoldersDaoImpl extends DaoGenericsImpl implements FoldersDao {


    private final FoldersApi foldersApi;

    EncriptacionAES encriptacionAES;

    CachedCredentials ca;
    @Inject
    public FoldersDaoImpl(FoldersApi foldersApi, EncriptacionAES encriptacionAES, CachedCredentials ca) {
        this.foldersApi = foldersApi;
        this.encriptacionAES = encriptacionAES;
        this.ca = ca;
    }


    @Override
    public Single<Either<String, Folder>> get(String nameUser, String nameFolder, String passFolder) {
        return safeSingleApicall(foldersApi.getFolder(nameUser, nameFolder, passFolder));
    }

    @Override
    public Single<Either<String, Response<Object>>> add(Folder f) {
        return safeSingleResponseApicall(foldersApi.addFolder(f));
    }

    @Override
    public Single<Either<String, List<Folder>>> getAll(String nameUser) {
        return safeSingleApicall(foldersApi.getAllFolders(nameUser));
    }

    @Override
    public Single<Either<String, Folder>> changePass(String userName, String folderName, String oldPass, String newPass) {
        return safeSingleApicall(foldersApi.changePass(userName, folderName, oldPass, newPass));
    }
}
