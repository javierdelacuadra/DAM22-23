package servicios.modelo.hibernate;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@NamedQueries({
        @NamedQuery(name = "HQL_GET_TABLE_BY_ID", query = "FROM TablesEntity c where c.id = :id"),})

@Entity
@Table(name = "tables", schema = "WebStore")
public class TablesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "table_number", nullable = false)
    private int tableNumber;
    @Basic
    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;
    @OneToMany(mappedBy = "table")
    @ToString.Exclude
    private List<OrdersEntity> ordersById;
}
