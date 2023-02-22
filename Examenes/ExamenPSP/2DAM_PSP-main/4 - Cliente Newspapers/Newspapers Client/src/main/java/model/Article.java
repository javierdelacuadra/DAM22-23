package model;

import lombok.Data;

import java.util.Objects;

@Data
public class Article {
    private int id;
    private String name_article;

    private String description;
    private int id_type;
    private int id_newspaper;

    public Article() {
    }

    public Article(int id, String name_article, String description, int id_type, int id_newspaper) {
        this.id = id;
        this.name_article = name_article;
        this.description = description;
        this.id_type = id_type;
        this.id_newspaper = id_newspaper;
    }

    public Article(String name_article, String description, int id_type, int id_newspaper) {
        this.name_article = name_article;
        this.description = description;
        this.id_type = id_type;
        this.id_newspaper = id_newspaper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id_newspaper == article.id_newspaper && Objects.equals(name_article, article.name_article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name_article, id_newspaper, id_type);
    }
}
