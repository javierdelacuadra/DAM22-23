package org.servidor.domain.servicios;

import org.utils.domain.modelo.Firma;
import org.utils.domain.modelo.User;

public interface ServiciosUser {
    User login(String username, Firma firma);

    String getCertificado(String username);

    User add(User user);

}
