package ui.screens.newspapers.delNews;

import lombok.Data;
import model.Article;
import model.Newspaper;

import java.util.List;

@Data
public class DelNewsState {

    private String error;
    private List<Article> articlesList;
    private List<Newspaper> newspaperList;

    private Newspaper newspaperSelected;

    private String warningMessage;

    private String successMessage;


    public DelNewsState(String error, List<Article> articlesList, List<Newspaper> newspaperList, Newspaper newspaperSelected, String warningMessage, String successMessage) {
        this.error = error;
        this.articlesList = articlesList;
        this.newspaperList = newspaperList;
        this.warningMessage = warningMessage;
        this.newspaperSelected = newspaperSelected;
        this.successMessage = successMessage;
    }
}
