package model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

//@NamedQueries({
//        @NamedQuery(name = "HQL_GET_ACTIVE_SUBSCRIPTIONS_BY_NEWSPAPER", query = "FROM Subscription s WHERE s.id_newspaper = :id AND cancellationDate IS NULL"),
//        @NamedQuery(name = "HQL_GET_ACTIVE_SUBSCRIPTIONS_BY_READER", query = "FROM Subscription s WHERE s.id_reader = :id AND cancellationDate IS NULL")})
public class Subscription {
    private int id_reader;
    private int id_newspaper;
    private LocalDate signingDate;
    private LocalDate cancellationDate;
    private Reader readerByIdReader;

    public Subscription(int id_reader, int id_newspaper, LocalDate signingDate, LocalDate cancellationDate) {
        this.id_reader = id_reader;
        this.id_newspaper = id_newspaper;
        this.signingDate = signingDate;
        this.cancellationDate = cancellationDate;
    }
}

//TODO: borrar