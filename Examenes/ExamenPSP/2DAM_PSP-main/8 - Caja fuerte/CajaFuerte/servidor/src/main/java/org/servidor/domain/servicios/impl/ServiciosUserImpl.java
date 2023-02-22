package org.servidor.domain.servicios.impl;

import jakarta.inject.Inject;
import org.servidor.dao.impl.UserDaoImpl;
import org.servidor.domain.errores.InvalidFieldsException;
import org.servidor.domain.servicios.ServiciosUser;
import org.servidor.domain.servicios.common.ServicesConstants;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.User;

public class ServiciosUserImpl implements ServiciosUser {

    UserDaoImpl userDao;

    @Inject
    public ServiciosUserImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override public User login(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()){
            return userDao.login(username, password);
        }
        throw new InvalidFieldsException(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
    }

    @Override public User add(User user) {
        if (!user.getName().isEmpty() && !user.getPassword().isEmpty()){
            if (userDao.add(user) > 0){
                return user;
            }
            throw new InvalidFieldsException(ServicesConstants.NO_SE_PUDO_AGREGAR_EL_USUARIO);
        }
        throw new InvalidFieldsException(AplicationConstants.COMPLETA_TODOS_LOS_CAMPOS);
    }
}
