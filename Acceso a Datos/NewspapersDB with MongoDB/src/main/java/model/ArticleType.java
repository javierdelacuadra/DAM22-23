package model;

import common.Constantes;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

//@NamedQueries({
//        @NamedQuery(name = "HQL_GET_ALL_TYPES", query = "SELECT t FROM ArticleType t"),
//        @NamedQuery(name = "HQL_GET_MOST_READ_TYPE", query = "SELECT article.type FROM ReadArticle read JOIN read.article article GROUP BY article.type ORDER BY COUNT(*) DESC LIMIT 1"),
//})
public class ArticleType {
    private int id;
    private String description;

    public ArticleType(String line) {
        String[] split = line.split(Constantes.PUNTO_Y_COMA);
        this.id = Integer.parseInt(split[0]);
        this.description = split[1];
    }

    public ArticleType(int id) {
        this.id = id;
    }
}

//TODO: borrar