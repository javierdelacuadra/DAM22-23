package com.example.servidorgraphql.domain.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conductor")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "camion_id", nullable = false)
    private int camionID;

    public Conductor(Integer id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Conductor(String nombre, String telefono, int camionID) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.camionID = camionID;
    }
}