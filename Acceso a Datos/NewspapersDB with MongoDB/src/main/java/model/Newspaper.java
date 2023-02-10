package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Newspaper {

    private ObjectId _id;
    private String name;
    private String releaseDate;
    private List<Article> articles;
    private List<Reader> readers;

    public Newspaper(String name, String releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public Newspaper(ObjectId _id, String text, String toString) {
        this._id = _id;
        this.name = text;
        this.releaseDate = toString;
    }
}
