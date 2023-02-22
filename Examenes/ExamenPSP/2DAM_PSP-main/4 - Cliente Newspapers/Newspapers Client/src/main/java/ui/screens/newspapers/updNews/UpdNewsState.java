package ui.screens.newspapers.updNews;

import lombok.Data;
import model.Newspaper;

import java.util.List;

@Data
public class UpdNewsState {
    private String error;

    private Newspaper newsSelected;

    private Integer newsPosition;
    private List<Newspaper> allNewspapers;

    private String confirmation;

    public UpdNewsState(String error, Newspaper newsSelected, Integer newsPosition, List<Newspaper> allNewspapers, String confirmation) {
        this.error = error;
        this.newsSelected = newsSelected;
        this.newsPosition = newsPosition;
        this.allNewspapers = allNewspapers;
        this.confirmation = confirmation;
    }
}
