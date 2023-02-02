package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class Login {
    private ObjectId _id;
    private String name;
    private String password;

    public Login(String name, String password) {
        this.name = name;
        this.password = password;
    }

}