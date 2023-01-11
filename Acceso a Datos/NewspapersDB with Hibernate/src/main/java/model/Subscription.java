package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "subscription")

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
}