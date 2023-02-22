package ui.screens.newspapers.updnews;

import lombok.Data;
import org.miutils.domain.modelo.Newspaper;

import java.util.List;

@Data
public class UpdNewsState {
    private String error;

    private Newspaper newsSelected;

    private Integer newsPosition;
    private List<Newspaper> allNewspapers;
    private boolean isLoading;

    private String confirmation;

    public UpdNewsState(String error, Newspaper newsSelected, Integer newsPosition, List<Newspaper> allNewspapers, boolean isLoading, String confirmation) {
        this.error = error;
        this.newsSelected = newsSelected;
        this.newsPosition = newsPosition;
        this.allNewspapers = allNewspapers;
        this.isLoading = isLoading;
        this.confirmation = confirmation;
    }
}
