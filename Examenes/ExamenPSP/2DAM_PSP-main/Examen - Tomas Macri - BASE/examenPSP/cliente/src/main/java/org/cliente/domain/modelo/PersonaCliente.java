package org.cliente.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PersonaCliente {

    private int id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String password;
    private String confirmPassword;





}