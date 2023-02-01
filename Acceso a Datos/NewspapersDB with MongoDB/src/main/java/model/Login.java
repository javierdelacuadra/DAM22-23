package model;

import lombok.*;

@Getter
@Setter
@ToString(exclude = {"reader"})
@AllArgsConstructor
@NoArgsConstructor

//@NamedQueries({
//        @NamedQuery(name = "HQL_GET_LOGIN", query = "SELECT l FROM Login l WHERE l.name = :name AND l.password = :password")
//})
public class Login {
    private String name;
    private String password;
    private Reader reader;

    public Login(String name, String password) {
        this.name = name;
        this.password = password;
    }

}