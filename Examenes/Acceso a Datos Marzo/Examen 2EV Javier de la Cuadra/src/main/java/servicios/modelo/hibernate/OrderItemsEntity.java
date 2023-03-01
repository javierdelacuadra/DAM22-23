package servicios.modelo.hibernate;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "order_items", schema = "WebStore")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ORDER_ITEMS_PURCHASED_BY_CLIENT_ID", query = "from OrderItemsEntity n where n.order.customer.id = :id"),
        @NamedQuery(name = "HQL_GET_ALL_ORDER_ITEMS", query = "select n from OrderItemsEntity n LEFT JOIN FETCH n.menuItem left join fetch n.order"),}
)
public class OrderItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private OrdersEntity order;
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    @ToString.Exclude
    private MenuItemsEntity menuItem;
    @Basic
    @Column(name = "quantity")
    private int quantity;
    @Basic
    @Column(name = "price")
    private double price;
}
