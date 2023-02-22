package domain.modelo;

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

    public Article(int id, String nameArt, String description, int idType, int idNews) {
        this.id = id;
        this.name_article = nameArt;
        this.description = description;
        this.id_type = idType;
        this.id_newspaper = idNews;
    }

    public Article(String nameArt, String description, int idType, int idNews) {
        this.name_article = nameArt;
        this.description = description;
        this.id_type = idType;
        this.id_newspaper = idNews;
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
