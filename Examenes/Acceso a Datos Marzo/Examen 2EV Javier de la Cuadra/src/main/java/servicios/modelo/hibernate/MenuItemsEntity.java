package servicios.modelo.hibernate;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@NamedQueries({
        @NamedQuery(name = "HQL_GET_MENU_ITEM_BY_NAME", query = "FROM MenuItemsEntity c where c.name = :name"),
        @NamedQuery(name = "HQL_GET_ITEMS_ORDERED_BY_CLIENT_ID", query = "SELECT n.menuItem from OrderItemsEntity n where n.order.customer.id = :id")})

@Entity
@Table(name = "menu_items", schema = "WebStore")
public class MenuItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "price")
    private double price;
}
