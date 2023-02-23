package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Informe {
    private String nombre;
    private LocalDate fecha;
    private String rol;
    private List<Raton> ratones;
}
