package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Article {
    private String name;
    private String type;
    private List<ReadArticle> readArticles;

    public Article(String name, String type) {
        this.name = name;
        this.type = type;
    }
}