package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "subscription")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ACTIVE_SUBSCRIPTIONS_BY_NEWSPAPER", query = "FROM Subscription s WHERE s.id_newspaper = :id AND cancellationDate IS NULL"),
        @NamedQuery(name = "HQL_GET_ACTIVE_SUBSCRIPTIONS_BY_READER", query = "FROM Subscription s WHERE s.id_reader = :id AND cancellationDate IS NULL")})
public class Subscription {
    @Id
    @Column(name = "id_reader")
    private int id_reader;
    @Id
    @Column(name = "id_newspaper")
    private int id_newspaper;
    @Column(name = "start_date")
    private LocalDate signingDate;
    @Column(name = "cancellation_date")
    private LocalDate cancellationDate;
    @ManyToOne
    @JoinColumn(name = "id_reader", referencedColumnName = "id", insertable = false, updatable = false)
    private Reader readerByIdReader;

    public Subscription(int id_reader, int id_newspaper, LocalDate signingDate, LocalDate cancellationDate) {
        this.id_reader = id_reader;
        this.id_newspaper = id_newspaper;
        this.signingDate = signingDate;
        this.cancellationDate = cancellationDate;
    }
}