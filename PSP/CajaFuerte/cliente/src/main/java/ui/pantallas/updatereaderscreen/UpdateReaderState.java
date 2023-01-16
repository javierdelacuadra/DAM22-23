package ui.pantallas.updatereaderscreen;

import lombok.AllArgsConstructor;
import model.Reader;

import java.util.List;

@AllArgsConstructor
public class UpdateReaderState {
    public String error;
    public List<Reader> readers;
}
