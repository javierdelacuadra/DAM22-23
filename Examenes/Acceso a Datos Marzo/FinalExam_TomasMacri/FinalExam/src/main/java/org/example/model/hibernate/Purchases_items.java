package org.example.model.hibernate;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.common.Queries;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "purchases_items")
@NamedQuery(name = "HQL_GET_ITEMS_PURCHASED_BY_CLIENT_NAME", query = Queries.FROM_PURCHASES_ITEMS_PI_WHERE_PI_PURCHASE_CLIENT_NAME_NAME_CLIENT)
@NamedQuery(name = "HQL_DELETE_PP_BY_ID", query = Queries.DELETE_PP_BY_ID)
public class Purchases_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "id_purchase", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private Purchase purchase;
    @ManyToOne
    @JoinColumn(name = "id_item")
    @ToString.Exclude
    private Item item;

    @Column(name = "amount")
    private int amount;

    // when it is not added to the db, we do not have the purchase id
    public Purchases_items(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    //for ex 5
    public Purchases_items(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public Purchases_items(Purchase purchase, Item item) {
        this.purchase = purchase;
        this.item = item;
    }

    public Purchases_items(Purchase purchase, Item item, int amount) {
        this.purchase = purchase;
        this.item = item;
        this.amount = amount;
    }
}
