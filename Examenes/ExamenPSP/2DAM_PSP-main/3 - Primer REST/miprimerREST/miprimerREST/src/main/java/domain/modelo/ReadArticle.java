package domain.modelo;

import lombok.Data;

@Data
public class ReadArticle {
    private int id;
    private int id_reader;
    private int id_article;
    private int rating;

    public ReadArticle() {

    }

    public ReadArticle(int id, int idRead, int idArt, int rating) {
        this.id = id;
        this.id_reader = idRead;
        this.id_article = idArt;
        this.rating = rating;
    }

    public ReadArticle(int idReader, int idArt, int rating) {
        this.id_reader = idReader;
        this.id_article = idArt;
        this.rating = rating;
    }
}
