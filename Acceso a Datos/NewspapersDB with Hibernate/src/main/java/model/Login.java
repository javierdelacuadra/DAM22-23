package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reader")
    private Reader reader;

    public Login(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Login(String name) {
        this.name = name;
    }
}