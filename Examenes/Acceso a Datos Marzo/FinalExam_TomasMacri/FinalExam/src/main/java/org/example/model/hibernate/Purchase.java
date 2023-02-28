package org.example.model.hibernate;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.common.Queries;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "purchases")

@NamedQuery(name = "HQL_ALL_PURCHASES", query = Queries.FROM_PURCHASE)
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @ToString.Exclude
    private Client client;

    @Column(name = "p_date")
    private LocalDate p_date;

    @Column(name = "total_cost")
    private double total_cost;

    @OneToMany(mappedBy = "purchase", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<Purchases_items> items_purchased;

    @Column(name = "paid")
    private int paid;

    public Purchase(int id) {
        this.id = id;
    }
}
