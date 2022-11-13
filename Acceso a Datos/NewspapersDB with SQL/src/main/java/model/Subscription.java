package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Subscription {
    private int id;
    private int id_reader;
    private int id_newspaper;
    private LocalDate signingDate;
    private LocalDate cancellationDate;
}