package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

//@NamedQueries({
//        @NamedQuery(name = "HQL_GET_ALL_READERS", query = "from Reader"),
//        @NamedQuery(name = "HQL_GET_READERS_BY_ID_NEWSPAPER", query = "SELECT r.readerByIdReader FROM Subscription r WHERE r.id_newspaper = :id_newspaper AND r.cancellationDate IS NULL"),
//        @NamedQuery(name = "HQL_GET_READERS_BY_ARTICLE_TYPE", query = "SELECT DISTINCT ra.reader FROM ReadArticle ra WHERE ra.article.type.description = :description")})
public class Reader {
    private int id;
    private String name;
    private String dateOfBirth;
    private String cancellationDate;

    public Reader(String name, String dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Reader(int id, String text, String value) {
        this.id = id;
        this.name = text;
        this.dateOfBirth = value;
    }
}