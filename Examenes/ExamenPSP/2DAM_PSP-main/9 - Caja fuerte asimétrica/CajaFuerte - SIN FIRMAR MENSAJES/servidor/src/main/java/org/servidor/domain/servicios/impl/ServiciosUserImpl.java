package org.servidor.domain.servicios.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.seguridad.impl.EncriptacionAES;
import org.seguridad.EncriptacionAsimetrica;
import org.servidor.config.Credentials;
import org.servidor.dao.impl.UserDaoImpl;
import org.servidor.domain.errores.InvalidFieldsException;
import org.servidor.domain.servicios.ServiciosUser;
import org.servidor.domain.servicios.common.ServicesConstants;
import org.servidor.jakarta.common.RestConstants;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Firma;
import org.utils.domain.modelo.User;

import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class ServiciosUserImpl implements ServiciosUser {

    private final UserDaoImpl userDao;

    private final Credentials credentials;

    private final EncriptacionAsimetrica asimetrica;

    private final EncriptacionAES encriptacionAES;
    @Inject
    public ServiciosUserImpl(UserDaoImpl userDao, Credentials credentials, EncriptacionAsimetrica asimetrica, EncriptacionAES encriptacionAES) {
        this.userDao = userDao;
        this.credentials = credentials;
        this.asimetrica = asimetrica;
        this.encriptacionAES = encriptacionAES;
    }

    @Override
    public User login(String username, Firma firma) {
        if (!username.isEmpty()) {
            User userLogin = userDao.login(username);
            try {
                Either<String, X509Certificate> getCert = asimetrica.getCertificado(userLogin.getCertificate());
                if (getCert.isLeft()) {
                    throw new InvalidFieldsException(getCert.getLeft());
                } else {
                    if (asimetrica.verificarfirma(getCert.get().getPublicKey(),firma)){
                        return userLogin;
                    }
                    throw new InvalidFieldsException(AplicationConstants.LOGIN_NO_VALIDO);
                }
            } catch (Exception e) {
                throw new InvalidFieldsException(e.getMessage());
            }
        }
        throw new InvalidFieldsException(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
    }

    @Override
    public String getCertificado(String username) {
        return userDao.getCertificate(username);
    }

    @Override
    public User add(User user) {
        if (!user.getName().isEmpty() && !user.getPassword().isEmpty()) {
            // Tenemos un usuario con:
            // password --> contraseña con la que encriptó su PK, encriptada con la PK del servidor codificada en Base64
            // publicKeyEncripted --> PK del usuario encriptada con una clave que está encriptada en el campo de arriba
            try {
                String password = asimetrica.desencriptarBase64ConClavePrivada(user.getPassword(), credentials.getPrivateKey());
                String publicKeyString = encriptacionAES.desencriptar(user.getPublicKeyEncripted(), password);
                Either<String, PublicKey> getPK = asimetrica.getPublicKeyFromStringBase64(publicKeyString);
                if (getPK.isRight()) {
                    PublicKey publicKey = getPK.get();
                    asimetrica.certificar(publicKey, RestConstants.CN + user.getName(), credentials.getPrivateKey(), RestConstants.ISSUER_NAME)
                            .peek(user::setCertificate)
                            .peekLeft(error -> {
                                throw new InvalidFieldsException(error);
                            });
                    if (userDao.add(user) > 0) {
                        return user;
                    }
                }
                throw new InvalidFieldsException(ServicesConstants.NO_SE_PUDO_AGREGAR_EL_USUARIO);
            } catch (InvalidFieldsException e){
                throw e;
            } catch (Exception e) {
                throw new InvalidFieldsException(ServicesConstants.NO_SE_PUDO_AGREGAR_EL_USUARIO);
            }
        }
        throw new InvalidFieldsException(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
    }
}
