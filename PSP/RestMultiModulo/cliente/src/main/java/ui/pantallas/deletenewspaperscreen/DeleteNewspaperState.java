package ui.pantallas.deletenewspaperscreen;

import lombok.AllArgsConstructor;
import modelo.Newspaper;

import java.util.List;

@AllArgsConstructor
public class DeleteNewspaperState {
    public String error;
    public List<Newspaper> newspapers;
}
