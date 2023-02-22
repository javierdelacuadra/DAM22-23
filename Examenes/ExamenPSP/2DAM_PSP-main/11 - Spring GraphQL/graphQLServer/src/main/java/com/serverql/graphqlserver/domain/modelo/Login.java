package com.serverql.graphqlserver.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {

    private String username;
    private String password;

    private String role;

}