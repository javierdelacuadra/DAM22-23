package ui.pantallas.addnewspaperscreen;

import lombok.AllArgsConstructor;
import modelo.Newspaper;

import java.util.List;

@AllArgsConstructor
public class AddNewspaperState {
    public String error;
    public List<Newspaper> newspapers;
}
