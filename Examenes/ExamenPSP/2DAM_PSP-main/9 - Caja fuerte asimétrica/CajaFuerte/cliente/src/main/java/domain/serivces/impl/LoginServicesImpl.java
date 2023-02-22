package domain.serivces.impl;

import common.CachedCredentials;
import config.Configuration;
import dao.impl.UserDaoImpl;
import domain.serivces.LoginServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.seguridad.EncriptacionAsimetrica;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Firma;
import org.utils.domain.modelo.User;
import org.utils.domain.modelo.dto.UserDTO;

import java.security.KeyStore;
import java.security.PrivateKey;

public class LoginServicesImpl implements LoginServices {

    private final UserDaoImpl loginDAOImpl;

    private final EncriptacionAsimetrica ea;

    private final Configuration configuration;

    private final CachedCredentials credentials;
    @Inject
    public LoginServicesImpl(UserDaoImpl loginDAOImpl, EncriptacionAsimetrica ea, Configuration configuration, CachedCredentials credentials) {
        this.loginDAOImpl = loginDAOImpl;
        this.ea = ea;
        this.configuration = configuration;
        this.credentials = credentials;
    }

    @Override
    public Single<Either<String, User>> login(String user, String password) {
        if (user.isEmpty() || password.isEmpty()) {
            return Single.just(Either.left(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS));
        }
        Either<String, KeyStore> getKeyStore = ea.getKeyStore(configuration.getGetUserCertsPath() + user + AplicationConstants.KEYSTORE_EXTENSION, password);
        if (getKeyStore.isLeft()) {
            return Single.just(Either.left(getKeyStore.getLeft()));
        }
        else {
            try {
                PrivateKey privateKey = ea.getPrivateKeyFromKeyStore(getKeyStore.get(), password).get();
                Firma f = ea.firmarConPrivateKey(privateKey, null);
                UserDTO userDTO = new UserDTO(user, f);
                credentials.setPublicKeyUserLogged(ea.getPublicKeyFromKeyStore(getKeyStore.get()));
                credentials.setPrivateKeyUserLogged(ea.getPrivateKeyFromKeyStore(getKeyStore.get(), password).get());
                credentials.setUserLogged(userDTO);
                return loginDAOImpl.login(userDTO);
            } catch (Exception e) {
                return Single.just(Either.left(e.getMessage()));
            }
        }
    }
}
