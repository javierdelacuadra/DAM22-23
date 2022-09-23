package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LevelQuery {
    private String text;
    private String difficulty;
    private boolean rated;
    private boolean featured;
    private boolean epic;
}