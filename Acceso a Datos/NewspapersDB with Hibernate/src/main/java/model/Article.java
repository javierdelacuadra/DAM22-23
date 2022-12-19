package model;

import common.Constantes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "article")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_ARTICLES", query = "from Article"),
        @NamedQuery(name = "HQL_GET_ARTICLE_BY_ID", query = "from Article n where n.id = :id"),
        @NamedQuery(name = "HQL_DELETE_ARTICLE_BY_ID", query = "delete from Article n where n.id = :id"),
        @NamedQuery(name = "HQL_UPDATE_ARTICLE_BY_ID", query = "update Article n set n.name_article = :name_article, n.id_type = :id_type, n.id_newspaper = :id_newspaper where n.id = :id")})
public class Article {
    @Id
    private int id;
    @Column(name = "name_article")
    private String name_article;
    @Column(name = "id_type")
    private int id_type;
    @Column(name = "id_newspaper")
    private int id_newspaper;

    public Article(String line) {
        String[] split = line.split(Constantes.PUNTO_Y_COMA);
        this.id = Integer.parseInt(split[0]);
        this.name_article = split[1];
        this.id_type = Integer.parseInt(split[2]);
        this.id_newspaper = Integer.parseInt(split[3]);
    }
}