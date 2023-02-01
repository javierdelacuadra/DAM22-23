package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

//@NamedQueries({
//        @NamedQuery(name = "HQL_GET_READARTICLE_BY_ID", query = "SELECT r FROM ReadArticle r WHERE r.id = :id"),
//        @NamedQuery(name = "HQL_GET_READARTICLE_BY_READER", query = "SELECT r FROM ReadArticle r WHERE r.reader.id = :id"),
//        @NamedQuery(name = "HQL_GET_READARTICLE_BY_ARTICLE", query = "SELECT r FROM ReadArticle r WHERE r.article.id = :id"),
//        @NamedQuery(name = "HQL_GET_READARTICLE_BY_READER_AND_ARTICLE", query = "FROM ReadArticle r WHERE r.reader.id = :idReader AND r.article.id = :idArticle"),
//        @NamedQuery(name = "HQL_GET_ALL_READARTICLE", query = "SELECT r FROM ReadArticle r"),
//        @NamedQuery(name = "HQL_UPDATE_READARTICLE", query = "UPDATE ReadArticle r SET r.rating = :rating WHERE r.reader.id = :idReader AND r.article.id = :idArticle"),
//        @NamedQuery(name = "HQL_DELETE_READ_ARTICLE_BY_NEWSPAPER_ID", query = "DELETE FROM ReadArticle r WHERE r.article.newspaper.id = :id"),
//        @NamedQuery(name = "HQL_GET_AVG_RATING_BY_READER", query = "SELECT AVG(r.rating) as avgRating, r.article.newspaper.name as name FROM ReadArticle r WHERE r.reader.id = :idReader GROUP BY r.article.newspaper.id"),
//})
public class ReadArticle {
    private int id;
    private int mark;
}