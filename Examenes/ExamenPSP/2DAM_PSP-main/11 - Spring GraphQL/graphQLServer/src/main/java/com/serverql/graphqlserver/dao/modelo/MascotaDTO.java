package com.serverql.graphqlserver.dao.modelo;

import com.serverql.graphqlserver.dao.common.DaoConstants;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DaoConstants.MASCOTAS)
public class MascotaDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DaoConstants.ID, nullable = false)
    private Integer id;

    @Column(name = DaoConstants.NAME, nullable = false)
    private String name;

    @Column(name = DaoConstants.TYPE, nullable = false)
    private String type;

    @Column(name = DaoConstants.AGE, nullable = false)
    private int age;
    @ManyToOne
    @JoinColumn(name = DaoConstants.ID_PERSONA)
    @ToString.Exclude
    private PersonaDTO persona;

    public MascotaDTO(Integer id, String name, String type, int edad) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.age = edad;
    }
}
