package ui.screens.newspapers.addNews;

import lombok.Data;
import model.Newspaper;

import java.util.List;

@Data
public class AddNewsState {

    private String error;
    private List<Newspaper> allNews;

    private String welcomeMessage;


    public AddNewsState(String error, List<Newspaper> allNews, String welcomeMessage) {
        this.error = error;
        this.allNews = allNews;
        this.welcomeMessage = welcomeMessage;
    }
}
