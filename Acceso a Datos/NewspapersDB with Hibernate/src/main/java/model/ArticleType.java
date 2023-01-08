package model;

import common.Constantes;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "type")
public class ArticleType {
    @Id
    private int id;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "type")
    private List<Article> articles;

    public ArticleType(String line) {
        String[] split = line.split(Constantes.PUNTO_Y_COMA);
        this.id = Integer.parseInt(split[0]);
        this.description = split[1];
    }

    public ArticleType(int id) {
        this.id = id;
    }
}