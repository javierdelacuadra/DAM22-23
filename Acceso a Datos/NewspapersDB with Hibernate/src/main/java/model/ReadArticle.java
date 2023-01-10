package model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "readarticle")
public class ReadArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_reader")
    private Reader reader;
    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;
    @Column(name = "rating")
    private int rating;

    public ReadArticle(Reader reader, Article article, int rating) {
        this.reader = reader;
        this.article = article;
        this.rating = rating;
    }
}