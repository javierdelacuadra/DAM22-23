package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "newspaper")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_NEWSPAPERS", query = "from Newspaper"),
        @NamedQuery(name = "HQL_GET_NEWSPAPER_BY_ID", query = "from Newspaper n where n.id = :id"),
        @NamedQuery(name = "HQL_DELETE_NEWSPAPER_BY_ID", query = "delete from Newspaper n where n.id = :id"),
        @NamedQuery(name = "HQL_UPDATE_NEWSPAPER_BY_ID", query = "update Newspaper n set n.name = :name, n.release_date = :release_date where n.id = :id"),
        @NamedQuery(name = "HQL_GET_ALL_ARTICLES_OF_SPECIFIC_NEWSPAPER", query = "SELECT a.id, a.name_article, t.description FROM Newspaper n, Article a, ArticleType t WHERE n.id = :newspaperId")})

public class Newspaper {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "release_date")
    private String release_date;
    @OneToMany
    @JoinColumn(name = "id_newspaper")
    private List<Article> articles;

    public Newspaper(String name, String release_date) {
        this.name = name;
        this.release_date = release_date;
    }

    public Newspaper(int id) {
        this.id = id;
    }

    public Newspaper(int id, String text, String toString) {
        this.id = id;
        this.name = text;
        this.release_date = toString;
    }

    @Override
    public String toString() {
        return id +
                ", name='" + name + '\'' +
                ", Date='" + release_date + '\'';
    }
}
