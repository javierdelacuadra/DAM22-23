package domain.serivces.impl;

import common.CommonConstants;
import config.Configuration;
import dao.impl.UserDaoImpl;
import domain.serivces.UserServices;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.seguridad.impl.EncriptacionAES;
import org.seguridad.EncriptacionAsimetrica;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.User;

import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.concurrent.CompletableFuture;

public class UserServicesImpl implements UserServices {

    private final UserDaoImpl userDao;

    private final EncriptacionAsimetrica ea;

    private final EncriptacionAES eaes;

    private final PublicKey pkServidor;

    private final Configuration configuration;

    @Inject
    public UserServicesImpl(UserDaoImpl userDao, EncriptacionAsimetrica ea, EncriptacionAES eaes, @Named(CommonConstants.SERVER_PK) PublicKey pkServidor, Configuration configuration) {
        this.userDao = userDao;
        this.ea = ea;
        this.eaes = eaes;
        this.pkServidor = pkServidor;
        this.configuration = configuration;
    }

    @Override
    public Either<String, User> add(User newUser) {
        Either<String, KeyPair> clavesNuevas = ea.generarClavesAsimetricas();
        if (clavesNuevas.isLeft()) {
            return Either.left(clavesNuevas.getLeft());
        } else {
            String passwordOriginal = newUser.getPassword();
            String clavePublicaBase64 = ea.getStringOfPublicKey(clavesNuevas.get().getPublic());
            newUser.setPublicKeyEncripted(eaes.encriptar(clavePublicaBase64, newUser.getPassword()));
            try {
                String claveCifradoAESCifrada = ea.encriptarConClavePublica(newUser.getPassword(), pkServidor);
                newUser.setPassword(claveCifradoAESCifrada);
            } catch (Exception e) {
                return Either.left(e.getMessage());
            }
            Either<String, User> updatedUser = addUser(newUser);
            if (updatedUser.isLeft()) {
                return updatedUser;
            }
            // Se convierte el password en un char[] para poder usarla en el KeyStore
            char[] secretKey = passwordOriginal.toCharArray();
            try {
                ea.createKeyStore(secretKey, clavesNuevas.get().getPrivate(), updatedUser.get().getCertificate(), Paths.get(configuration.getGetUserCertsPath() + updatedUser.get().getName() + AplicationConstants.KEYSTORE_EXTENSION));
            } catch (Exception e) {
                return Either.left(e.getMessage());
            }
            return updatedUser;
        }
    }

    private Either<String, User> addUser(User newUser) {
        CompletableFuture<Either<String, User>> userCompletableFuture = new CompletableFuture<>();
        userDao.add(newUser).subscribe(userCompletableFuture::complete);
        return userCompletableFuture.join();
    }
}
