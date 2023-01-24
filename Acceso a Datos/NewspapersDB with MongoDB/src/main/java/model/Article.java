package model;

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
        @NamedQuery(name = "HQL_GET_ALL_ARTICLES_AND_TYPES", query = "from Article a JOIN FETCH a.type"),
        @NamedQuery(name = "HQL_GET_ARTICLE_BY_ID", query = "from Article n where n.id = :id"),
        @NamedQuery(name = "HQL_DELETE_ARTICLE_BY_NEWSPAPER_ID", query = "delete from Article n where n.newspaper.id = :id"),
        @NamedQuery(name = "HQL_GET_ARTICLES_BY_TYPE", query = "from Article a JOIN FETCH a.type where a.type.description = :description"),
        @NamedQuery(name = "HQL_GET_ARTICLES_BY_NEWSPAPER", query = "FROM Article a JOIN FETCH a.type WHERE a.newspaper.id = :newspaperID"),
        @NamedQuery(name = "HQL_GET_NUMBER_ARTICLES_BY_NEWSPAPER", query = "SELECT COUNT(a) as numberArticles, a.type.description as type FROM Article a WHERE a.newspaper.id = :newspaperID GROUP BY a.type.description"),
})
public class Article {
    @Id
    private int id;
    @Column(name = "name_article")
    private String name_article;
    @ManyToOne
    @JoinColumn(name = "id_type")
    private ArticleType type;
    @ManyToOne
    @JoinColumn(name = "id_newspaper")
    private Newspaper newspaper;
}