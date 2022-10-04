package modelo;

import lombok.Data;

@Data
public class ReadArticle {
    private int id;
    private int idReader;
    private int idArticle;
    private String readDate;
    private int rating;
}
