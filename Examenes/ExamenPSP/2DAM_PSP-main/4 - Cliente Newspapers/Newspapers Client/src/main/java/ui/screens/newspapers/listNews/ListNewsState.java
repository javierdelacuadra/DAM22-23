package ui.screens.newspapers.listNews;

import lombok.Data;
import model.Newspaper;

import java.util.List;

@Data
public class ListNewsState {

    private String error;
    private List<Newspaper> newspapers;

    private Newspaper newspaperSelected;
    private List<String> oldestReaders;

    public ListNewsState(String error, List<Newspaper> newspapers, Newspaper newspaperSelected, List<String> oldestReaders) {
        this.error = error;
        this.newspapers = newspapers;
        this.oldestReaders = oldestReaders;
        this.newspaperSelected = newspaperSelected;

    }
}
