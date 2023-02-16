package domain.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "camiones")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "fecha_construccion")
    private String fechaConstruccion;

}