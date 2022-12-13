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
        @NamedQuery(name = "HQL_UPDATE_READER_BY_ID", query = "update Reader n set n.name = :name, n.dateOfBirth = :date_of_birth where n.id = :id")})
public class Reader {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
}