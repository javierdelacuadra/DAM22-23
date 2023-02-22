package org.utils.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class User {
    private String name;
    private String password;

    private String publicKeyEncripted;

    private String certificate;
    private Rol role;

    public User() {

    }
    public User(String name, Rol rol) {
        this.name = name;
        this.role = rol;
    }
}
