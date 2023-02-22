package org.servidor.domain.servicios;

import org.utils.domain.modelo.User;

public interface ServiciosUser {
    User login(String username, String password);

    User add(User user);

}
