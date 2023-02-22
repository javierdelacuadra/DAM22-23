package ui.screens.readarticles.addReadArt;

import model.Article;
import model.Newspaper;
import model.QueryBadRatedArticles;
import model.Reader;
import lombok.Data;

import java.util.List;

@Data
public class AddReadArtState {

    private String error;

    private Reader readerSelected;
    private List<Newspaper> newspapers;

    private Newspaper newspaperSelected;

    private List<Article> articlesAvailable;

    private List<QueryBadRatedArticles> ratedWellArticles;

    private Integer currentRating;
    private String success;

    public AddReadArtState(String error, Reader readerSelected, List<Newspaper> newspapers, Newspaper newspaperSelected, List<Article> articlesAvailable, List<QueryBadRatedArticles> ratedWellArticles, Integer currentRating, String success) {
        this.error = error;
        this.readerSelected = readerSelected;
        this.newspapers = newspapers;
        this.newspaperSelected = newspaperSelected;
        this.articlesAvailable = articlesAvailable;
        this.ratedWellArticles = ratedWellArticles;
        this.currentRating = currentRating;
        this.success = success;
    }

}
