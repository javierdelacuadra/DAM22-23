package ui.screens.newspapers.listnews;

import lombok.Data;
import org.miutils.domain.modelo.Newspaper;

import java.util.List;

@Data
public class ListNewsState {

    private String error;
    private List<Newspaper> newspapers;

    private Newspaper newspaperSelected;
    private List<String> oldestReaders;

    private boolean isLoading;

    private String warningMessage;
    private String successMessage;

    public ListNewsState(String error, List<Newspaper> newspapers, Newspaper newspaperSelected, List<String> oldestReaders, boolean isLoading, String warningMessage, String successMessage) {
        this.error = error;
        this.newspapers = newspapers;
        this.oldestReaders = oldestReaders;
        this.newspaperSelected = newspaperSelected;
        this.isLoading = isLoading;
        this.warningMessage = warningMessage;
        this.successMessage = successMessage;

    }
}
