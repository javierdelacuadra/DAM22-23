package ui.pantallas.updatereaderscreen;

import lombok.AllArgsConstructor;
import modelo.Reader;

import java.util.List;

@AllArgsConstructor
public class UpdateReaderState {
    public String error;
    public List<Reader> readers;
}
