package ui.pantallas.updatenewspaperscreen;

import lombok.AllArgsConstructor;
import model.Newspaper;

import java.util.List;

@AllArgsConstructor
public class UpdateNewspaperState {
    public String error;
    public List<Newspaper> newspapers;
}
