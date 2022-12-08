package ui.pantallas.addreaderscreen;

import lombok.AllArgsConstructor;
import model.Reader;

import java.util.List;

@AllArgsConstructor
public class AddReaderState {
    public String error;
    public List<Reader> readers;
}