package domain.modelo;

import lombok.Data;

@Data
public class QueryBadRatedArticles {
    private String name_article;
    private boolean bad_rater;

    public QueryBadRatedArticles(String nameArt, boolean badRater) {
        this.name_article = nameArt;
        this.bad_rater = badRater;
    }
}
