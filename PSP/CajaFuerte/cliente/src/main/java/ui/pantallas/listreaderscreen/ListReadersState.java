package ui.pantallas.listreaderscreen;

import lombok.AllArgsConstructor;
import modelo.Reader;

import java.util.List;

@AllArgsConstructor
public class ListReadersState {
    public String error;
    public List<Reader> readers;
}