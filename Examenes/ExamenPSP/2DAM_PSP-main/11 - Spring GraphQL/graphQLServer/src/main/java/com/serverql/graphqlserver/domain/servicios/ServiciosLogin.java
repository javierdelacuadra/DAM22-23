package com.serverql.graphqlserver.domain.servicios;

import com.serverql.graphqlserver.dao.LoginDao;
import com.serverql.graphqlserver.dao.modelo.DataMapper;
import com.serverql.graphqlserver.domain.modelo.Login;
import com.serverql.graphqlserver.spring.security.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServiciosLogin {

    private final LoginDao loginDao;

    private final PasswordEncoder passwordEncoder;
    private final DataMapper dataMapper;

    public ServiciosLogin(LoginDao loginDao, PasswordEncoder passwordEncoder, DataMapper dataMapper) {
        this.loginDao = loginDao;
        this.passwordEncoder = passwordEncoder;
        this.dataMapper = dataMapper;
    }

    public Login login(String username, String password) {
        Login l = loginDao.findByLogin(username)
                .map(dataMapper::loginDTOToLogin)
                .orElse(null);

        if (l != null && !passwordEncoder.getBCryptPasswordEncoder().matches(password, l.getPassword())) {
            // Si hay alguien con ese username pero las contraseñas no coinciden, no hay nadie
            l = null;
        }
        return l;
    }

}
