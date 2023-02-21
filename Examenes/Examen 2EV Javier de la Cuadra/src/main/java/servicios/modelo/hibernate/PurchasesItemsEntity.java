package servicios.modelo.hibernate;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "purchases_items", schema = "WebStore")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALLasdas_ARTICLES", query = "from PurchasesEntity "),
        @NamedQuery(name = "HQL_GET_PURCHASED_ITEMS_BY_ID_CLIENT", query = "from PurchasesItemsEntity n where n.purchases.id = :id")})
public class PurchasesItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_purchase")
    private PurchasesEntity purchases;
    @ManyToOne
    @JoinColumn(name = "id_item")
    private ItemsEntity items;
    @Basic
    @Column(name = "amount")
    private Integer amount;

    public PurchasesItemsEntity(ItemsEntity items, Integer amount) {
        this.items = items;
        this.amount = amount;
    }
}
