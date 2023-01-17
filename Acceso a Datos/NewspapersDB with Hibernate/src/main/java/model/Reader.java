package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "readers")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_READERS", query = "from Reader"),
        @NamedQuery(name = "HQL_GET_READERS_BY_ID_NEWSPAPER", query = "SELECT r FROM Reader r JOIN Subscription s ON r.id = s.id_reader WHERE s.id_newspaper = :id_newspaper AND s.cancellationDate IS NULL"),
        //TODO: simplify query without using subquery or join
        @NamedQuery(name = "HQL_GET_READERS_BY_ARTICLE_TYPE", query = "SELECT DISTINCT ra.reader FROM ReadArticle ra WHERE ra.article.type.description = :description")})
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @OneToOne(mappedBy = "reader", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Login login;

    public Reader(String name, LocalDate dateOfBirth, Login login) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.login = login;
    }

    public Reader(int id, String text, LocalDate value) {
        this.id = id;
        this.name = text;
        this.dateOfBirth = value;
    }
}