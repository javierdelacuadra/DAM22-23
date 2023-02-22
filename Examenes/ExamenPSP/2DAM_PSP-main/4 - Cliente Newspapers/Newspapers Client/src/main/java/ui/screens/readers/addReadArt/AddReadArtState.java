package ui.screens.readers.addReadArt;

import model.Article;
import model.Newspaper;
import model.Reader;
import lombok.Data;

import java.util.List;

@Data
public class AddReadArtState {

    private String error;

    private Reader readerSelected;
    private List<Newspaper> newspapers;

    private Newspaper newspaperSelected;

    private List<Article> unreadArticles;

    private String success;

    public AddReadArtState(String error, Reader readerSelected, List<Newspaper> newspapers, Newspaper newspaperSelected, List<Article> unreadArticles, String success) {
        this.error = error;
        this.readerSelected = readerSelected;
        this.newspapers = newspapers;
        this.newspaperSelected = newspaperSelected;
        this.unreadArticles = unreadArticles;
        this.success = success;
    }

}
