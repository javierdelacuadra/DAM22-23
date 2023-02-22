package domain.serivces.impl;

import common.CachedCredentials;
import dao.impl.FoldersDaoImpl;
import dao.impl.UserDaoImpl;
import domain.serivces.FoldersServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.seguridad.EncriptacionAsimetrica;
import org.utils.domain.modelo.Folder;
import retrofit2.Response;

import java.security.PublicKey;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FolderServicesImpl implements FoldersServices {

    private final FoldersDaoImpl foldersDao;

    private final UserDaoImpl userDao;
    private final EncriptacionAsimetrica encriptacionAsimetrica;

    private final CachedCredentials ca;

    @Inject
    public FolderServicesImpl(FoldersDaoImpl foldersDao, UserDaoImpl userDao, EncriptacionAsimetrica encriptacionAsimetrica, CachedCredentials ca) {
        this.foldersDao = foldersDao;
        this.userDao = userDao;
        this.encriptacionAsimetrica = encriptacionAsimetrica;
        this.ca = ca;
    }

    @Override
    public Single<Either<String, Folder>> get(String nameUser, String nameFolder, String passFolder) {
        CompletableFuture<Single<Either<String, Folder>>> callFuture = new CompletableFuture<>();
        foldersDao.get(nameUser, nameFolder).subscribe(f -> {
            if (f.isRight()) {
                try {
                    if (encriptacionAsimetrica.desencriptarBase64ConClavePrivada(f.get().getPassword(), ca.getPrivateKeyUserLogged()).equals(passFolder)) {
                        callFuture.complete(Single.just(f));
                    } else {
                        callFuture.complete(Single.just(Either.left("Carpeta o contraseña incorrecta")));
                    }
                } catch (Exception e) {
                    callFuture.complete(Single.just(Either.left("No tienes permisos para acceder a esta carpeta")));
                }
            } else {
                callFuture.complete(Single.just(f));
            }
        });
        return callFuture.join();
    }

    @Override
    public Single<Either<String, Response<Object>>> add(Folder f) {
        try {
            PublicKey publicKeyFuturoDueno;
            if (ca.getUserLogged().getName().equals(f.getNameUserWithAccess())) {
                publicKeyFuturoDueno = ca.getPublicKeyUserLogged();
            } else {
                //Se quiere compartir con otro usuario, cogemos su clave publica
                CompletableFuture<PublicKey> getPublicKeyACompartir = new CompletableFuture<>();
                userDao.getCertificate(f.getNameUserWithAccess()).subscribe(cert -> {
                    if (cert.isRight()) {
                        encriptacionAsimetrica.getPublicKeyFromCertificateEncoded(cert.get())
                                .map(getPublicKeyACompartir::complete)
                                .mapLeft(error -> getPublicKeyACompartir.completeExceptionally(new RuntimeException(error)));
                    } else {
                        getPublicKeyACompartir.completeExceptionally(new RuntimeException(cert.getLeft()));
                    }
                });
                publicKeyFuturoDueno = getPublicKeyACompartir.join();
            }
            f.setPassword(encriptacionAsimetrica.encriptarConClavePublica(f.getPassword(), publicKeyFuturoDueno));
            return foldersDao.add(f);
        } catch (Exception e) {
            return Single.just(Either.left("Error al añadir la carpeta"));
        }
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
