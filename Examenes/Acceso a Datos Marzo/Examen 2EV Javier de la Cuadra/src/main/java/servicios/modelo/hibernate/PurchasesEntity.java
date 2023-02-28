package servicios.modelo.hibernate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "purchases", schema = "DockerTest")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALaL_ARTICLES", query = "DELETE from PurchasesEntity p where p.id = :id"),
        @NamedQuery(name = "HQL_GET_ITEMS_PURCHASED_BY_CLIENT_ID", query = "SELECT n.items from PurchasesItemsEntity n where n.purchases.clientsByIdClient.id = :id")})
public class PurchasesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @ToString.Exclude
    private ClientsEntity clientsByIdClient;

    @Basic
    @Column(name = "p_date")
    private LocalDate pDate;

    @Basic
    @Column(name = "total_cost")
    private Double totalCost;

    @Basic
    @Column(name = "paid", nullable = false)
    private int paid;

    @OneToMany(mappedBy = "purchases", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<PurchasesItemsEntity> purchasesItems;

}
