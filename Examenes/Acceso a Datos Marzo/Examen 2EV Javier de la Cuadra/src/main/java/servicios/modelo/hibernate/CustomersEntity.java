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
        @NamedQuery(name = "HQL_GET_CUSTOMER_BY_EMAIL", query = "SELECT c FROM CustomersEntity c JOIN fetch c.orders where c.email = :email"),
        @NamedQuery(name = "HQL_GET_CUSTOMER_BY_NAME", query = "SELECT c FROM CustomersEntity c WHERE c.firstName = :firstName and c.lastName = :lastName"),})

@Entity
@Table(name = "customers", schema = "WebStore")
public class CustomersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;
    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<OrdersEntity> orders;
}