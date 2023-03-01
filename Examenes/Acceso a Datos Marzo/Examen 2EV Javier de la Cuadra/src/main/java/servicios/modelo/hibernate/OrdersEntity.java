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
@Table(name = "orders", schema = "WebStore")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_ORDERS", query = "select n from OrdersEntity n LEFT JOIN FETCH n.orderItems"),})
public class OrdersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "table_id")
    @ToString.Exclude
    private TablesEntity table;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private CustomersEntity customer;
    @Basic
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Basic
    @Column(name = "total")
    private double total;
    @OneToMany(mappedBy = "order", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private List<OrderItemsEntity> orderItems;
}
