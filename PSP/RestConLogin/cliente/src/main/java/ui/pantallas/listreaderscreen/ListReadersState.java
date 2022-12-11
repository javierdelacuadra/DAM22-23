package ui.pantallas.listreaderscreen;

import lombok.AllArgsConstructor;
import model.Reader;

import java.util.List;

@AllArgsConstructor
public class ListReadersState {
    public String error;
    public List<Reader> readers;
}