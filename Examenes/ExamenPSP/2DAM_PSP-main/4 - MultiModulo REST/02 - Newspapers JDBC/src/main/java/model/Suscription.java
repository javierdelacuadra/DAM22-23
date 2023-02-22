package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Suscription {

    private int id_reader;
    private int id_newspaper;
    private LocalDate signing_date;
    private LocalDate cancellation_date;

    public Suscription(int id_reader, int id_newspaper, LocalDate signing_date, LocalDate cancellation_date) {
        this.id_reader = id_reader;
        this.id_newspaper = id_newspaper;
        this.signing_date = signing_date;
        this.cancellation_date = cancellation_date;
    }

    public Suscription(int id_reader, int id_newspaper) {
        this.id_reader = id_reader;
        this.id_newspaper = id_newspaper;
    }
}
