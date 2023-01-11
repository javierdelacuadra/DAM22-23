package model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"reader"})
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "login")
@NamedQueries({
        @NamedQuery(name = "HQL_GET_LOGIN", query = "SELECT l FROM Login l WHERE l.name = :name AND l.password = :password")
})
public class Login {
    @Id
    private String name;
    @Column(name = "password")
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.ALL})
    @JoinColumn(name = "id_reader", referencedColumnName = "id", nullable = false)
    private Reader reader;

    public Login(String name, String password) {
        this.name = name;
        this.password = password;
    }

}