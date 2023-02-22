package org.utils.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    String name;
    String password;
    Rol role;

    public User() {

    }

    public User(String name, Rol rol) {
        this.name = name;
        this.role = rol;
    }
}
