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

@Entity
@Table(name = "purchases", schema = "WebStore")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALaL_ARTICLES", query = "DELETE from PurchasesEntity p where p.id = :id"),
        @NamedQuery(name = "HQL_GET_PURCHASES_BY_CLIENT_ID", query = "from PurchasesEntity n where n.clientsByIdClient.id = :id")})
public class PurchasesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id", nullable = false)
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
    @OneToMany(mappedBy = "purchases", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.ALL})
    private List<PurchasesItemsEntity> purchasesItems;

    public PurchasesEntity(ClientsEntity clientsByIdClient, LocalDate pDate, Double totalCost, int paid, List<PurchasesItemsEntity> purchasesItems) {
        this.clientsByIdClient = clientsByIdClient;
        this.pDate = pDate;
        this.totalCost = totalCost;
        this.paid = paid;
        this.purchasesItems = purchasesItems;
    }

    @Override
    public String toString() {
        return "PurchasesEntity{" +
                "id=" + id +
                ", clientsByIdClient=" + clientsByIdClient +
                ", pDate=" + pDate +
                ", totalCost=" + totalCost +
                ", paid=" + paid +
                '}';
    }
}
