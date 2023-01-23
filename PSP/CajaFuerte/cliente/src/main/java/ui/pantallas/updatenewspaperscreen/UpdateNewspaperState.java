package ui.pantallas.updatenewspaperscreen;

import lombok.AllArgsConstructor;
import modelo.Newspaper;

import java.util.List;

@AllArgsConstructor
public class UpdateNewspaperState {
    public String error;
    public List<Newspaper> newspapers;
}
