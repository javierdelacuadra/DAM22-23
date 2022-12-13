package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "newspaper")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_NEWSPAPERS", query = "from Newspaper"),
        @NamedQuery(name = "HQL_GET_NEWSPAPER_BY_ID", query = "from Newspaper n where n.id = :id"),
        @NamedQuery(name = "HQL_DELETE_NEWSPAPER_BY_ID", query = "delete from Newspaper n where n.id = :id"),
        @NamedQuery(name = "HQL_UPDATE_NEWSPAPER_BY_ID", query = "update Newspaper n set n.name = :name, n.release_date = :release_date where n.id = :id")})

public class Newspaper {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "release_date")
    private String release_date;

    @Override
    public String toString() {
        return id +
                ", name='" + name + '\'' +
                ", Date='" + release_date + '\'';
    }
}
