package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "readers")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_READERS", query = "from Reader"),
        @NamedQuery(name = "HQL_GET_READER_BY_ID", query = "from Reader n where n.id = :id"),
        @NamedQuery(name = "HQL_DELETE_READER_BY_ID", query = "delete from Reader n where n.id = :id"),
        @NamedQuery(name = "HQL_UPDATE_READER_BY_ID", query = "update Reader n set n.name = :name, n.dateOfBirth = :date_of_birth where n.id = :id"),
        @NamedQuery(name = "HQL_GET_READERS_BY_ARTICLE_TYPE", query = "SELECT r FROM Reader r WHERE r.id IN (SELECT ra.id_reader FROM ReadArticle ra WHERE ra.id IN ( SELECT a.id FROM Article a WHERE a.id IN (SELECT t.id FROM ArticleType t WHERE t.description = :description)))")})
public class Reader {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
}