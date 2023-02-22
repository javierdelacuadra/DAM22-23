package domain.modelo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Suscription {

    private int id_reader;
    private int id_newspaper;
    private LocalDate signing_date;
    private LocalDate cancellation_date;

    public Suscription() {
    }

    public Suscription(int idReader, int idNews) {
        this.id_reader = idReader;
        this.id_newspaper = idNews;
    }
}
