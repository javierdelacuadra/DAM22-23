package com.example.data.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name = "newspaper")

public class NewspaperEntity {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "release_date")
    private String release_date;

    public NewspaperEntity(int id, String name, String releaseDate) {
        this.id = id;
        this.name = name;
        this.release_date = releaseDate;
    }
}
