package modelo;

import common.Constantes;
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
        String[] split = line.split(Constantes.PUNTO_Y_COMA);
        this.id = Integer.parseInt(split[0]);
        this.nameArticle = split[1];
        this.idType = Integer.parseInt(split[2]);
        this.idNewspaper = Integer.parseInt(split[3]);
    }

    public String toLine() {
        return id + Constantes.PUNTO_Y_COMA + nameArticle + Constantes.PUNTO_Y_COMA + idType + Constantes.PUNTO_Y_COMA + idNewspaper + Constantes.ESPACIO;
    }
}