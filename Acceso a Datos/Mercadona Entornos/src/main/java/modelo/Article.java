package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {
    private int id;
    private String nameArticle;
    private int idType;
    private int idNewspaper;

    public Article(String line) {
        String[] split = line.split(";");
        this.id = Integer.parseInt(split[0]);
        this.nameArticle = split[1];
        this.idType = Integer.parseInt(split[2]);
        this.idNewspaper = Integer.parseInt(split[3]);
    }

    public String toLine() {
        return id + ";" + nameArticle + ";" + idType + ";" + idNewspaper + "\n";
    }
}