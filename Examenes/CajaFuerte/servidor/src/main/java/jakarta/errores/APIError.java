package jakarta.errores;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class APIError {
    private String mensaje;
    private LocalDate fecha;
}
