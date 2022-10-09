package modelo;

import common.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleType {
    private int ID;
    private String description;

    public ArticleType(String line) {
        String[] split = line.split(Constantes.PUNTO_Y_COMA);
        this.ID = Integer.parseInt(split[0]);
        this.description = split[1];
    }
}