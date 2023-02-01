package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

//@NamedQueries({
//        @NamedQuery(name = "HQL_GET_ALL_NEWSPAPERS", query = "select n from Newspaper n LEFT JOIN FETCH n.articles"),
//        @NamedQuery(name = "HQL_GET_NEWSPAPER_BY_ID", query = "from Newspaper n left join fetch n.articles where n.id = :id"),
//        @NamedQuery(name = "HQL_GET_ALL_ARTICLES_OF_SPECIFIC_NEWSPAPER", query = "SELECT a.id, a.name_article, t.description FROM Newspaper n, Article a, ArticleType t WHERE n.id = :newspaperId")})

public class Newspaper {

    private ObjectId _id;
    private String name;
    private String releaseDate;
    private List<Article> articles;
    private List<Reader> readers;

    public Newspaper(String name, String releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public Newspaper(ObjectId _id, String text, String toString) {
        this._id = _id;
        this.name = text;
        this.releaseDate = toString;
    }
}
