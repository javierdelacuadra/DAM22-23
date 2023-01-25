package ui.pantallas.addreaderscreen;

import lombok.AllArgsConstructor;
import modelo.Usuario;

import java.util.List;

@AllArgsConstructor
public class AddReaderState {
    public String error;
    public List<Usuario> usuarios;
}