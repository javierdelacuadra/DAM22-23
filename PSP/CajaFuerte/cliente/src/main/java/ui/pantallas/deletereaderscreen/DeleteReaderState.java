package ui.pantallas.deletereaderscreen;

import lombok.AllArgsConstructor;
import modelo.Reader;

import java.util.List;

@AllArgsConstructor
public class DeleteReaderState {
    public String error;
    public List<Reader> readers;
}
