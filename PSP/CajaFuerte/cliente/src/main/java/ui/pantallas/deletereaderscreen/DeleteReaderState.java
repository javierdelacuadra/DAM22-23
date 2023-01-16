package ui.pantallas.deletereaderscreen;

import lombok.AllArgsConstructor;
import model.Reader;

import java.util.List;

@AllArgsConstructor
public class DeleteReaderState {
    public String error;
    public List<Reader> readers;
}
