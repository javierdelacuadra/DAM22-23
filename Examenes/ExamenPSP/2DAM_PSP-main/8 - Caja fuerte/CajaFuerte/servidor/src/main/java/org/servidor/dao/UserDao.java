package org.servidor.dao;

import org.utils.domain.modelo.User;

public interface UserDao {
    User login(String username, String password);

    int add(User user);

}
