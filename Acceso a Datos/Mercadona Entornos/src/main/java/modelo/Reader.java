package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Reader {
    private int id;
    private String name;
    private String birthDate;
    private List<Newspaper> subscriptions;
    private List<Article> articlesRead;
}
