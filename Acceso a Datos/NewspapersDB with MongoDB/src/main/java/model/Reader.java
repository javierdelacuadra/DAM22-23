package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

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