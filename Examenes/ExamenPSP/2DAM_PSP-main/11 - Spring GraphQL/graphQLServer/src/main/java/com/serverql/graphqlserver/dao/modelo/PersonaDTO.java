package com.serverql.graphqlserver.dao.modelo;

import com.serverql.graphqlserver.dao.common.DaoConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DaoConstants.PERSONAS)
public class PersonaDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DaoConstants.ID, nullable = false)
    private int id;

    @Column(name = DaoConstants.NAME, nullable = false)
    private String name;

    @Column(name = DaoConstants.SURNAME, nullable = false)
    private String surname;

    @OneToOne(mappedBy = "persona", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "id_persona")
    @ToString.Exclude
    private LoginDTO login;

    @OneToMany(mappedBy = DaoConstants.PERSONA)
    @ToString.Exclude
    private List<MascotaDTO> mascotas;

    public PersonaDTO(int id, String name, String surname, List<MascotaDTO> mascotas) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mascotas = mascotas;
    }
}
