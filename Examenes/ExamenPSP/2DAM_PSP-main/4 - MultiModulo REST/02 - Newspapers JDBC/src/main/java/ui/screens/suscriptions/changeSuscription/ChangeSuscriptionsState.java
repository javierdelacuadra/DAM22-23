package ui.screens.suscriptions.changeSuscription;

import lombok.Data;
import model.Newspaper;
import model.Reader;

import java.util.List;

@Data

public class ChangeSuscriptionsState {

    private String error;
    private List<Newspaper> newsSuscribed;
    private List<Newspaper> newsNotSuscribed;
    private Reader currentReader;
    private Newspaper newspaperSelected;
    private Boolean isSuscribedToNewsSelected;
    private String success;

    public ChangeSuscriptionsState(String error, List<Newspaper> newsSuscribed, List<Newspaper> newsNotSuscribed, Reader currentReader, Newspaper newspaperSelected, Boolean isSuscribedToNewsSelected, String success) {
        this.error = error;
        this.newsSuscribed = newsSuscribed;
        this.newsNotSuscribed = newsNotSuscribed;
        this.currentReader = currentReader;
        this.newspaperSelected = newspaperSelected;
        this.isSuscribedToNewsSelected = isSuscribedToNewsSelected;
        this.success = success;
    }
}
