package modelo;

import lombok.Data;

@Data
public class Subscription {
    private int id;
    private int idReader;
    private int idNewspaper;
    private String signingDate;
    private String cancellationDate;
}