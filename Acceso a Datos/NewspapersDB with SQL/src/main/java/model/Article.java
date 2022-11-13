package model;

import common.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private int id;
    private String name_article;
    private int id_type;
    private int id_newspaper;

    public Article(String line) {
        String[] split = line.split(Constantes.PUNTO_Y_COMA);
        this.id = Integer.parseInt(split[0]);
        this.name_article = split[1];
        this.id_type = Integer.parseInt(split[2]);
        this.id_newspaper = Integer.parseInt(split[3]);
    }

}