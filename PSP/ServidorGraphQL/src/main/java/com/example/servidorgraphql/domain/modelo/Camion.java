package com.example.servidorgraphql.domain.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "camion")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "fecha_construccion", nullable = false)
    private String fechaConstruccion;

    @Column(name = "empresaID", nullable = false)
    private int empresaID;

    public Camion(Integer id, String modelo, String fechaConstruccion) {
        this.id = id;
        this.modelo = modelo;
        this.fechaConstruccion = fechaConstruccion;
    }
}