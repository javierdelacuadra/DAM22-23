package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "readarticle")
public class ReadArticle {
    @Id
    private int id;
    @Column(name = "id_reader")
    private int id_reader;
    @Column(name = "id_article")
    private int id_article;
    @Column(name = "rating")
    private int rating;
}