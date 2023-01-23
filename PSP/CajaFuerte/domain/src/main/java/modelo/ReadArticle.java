package modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReadArticle {
    private int id;
    private int id_reader;
    private int id_article;
    private int rating;
}
