package servicios.modelo.hibernate;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "clients", schema = "DockerTest")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ALL_CLIENTS", query = "SELECT c FROM ClientsEntity c"),
        @NamedQuery(name = "HQL_GET_CLIENT_BY_ID", query = "SELECT c FROM ClientsEntity c JOIN fetch c.purchasesById WHERE c.id = :id"),
        @NamedQuery(name = "HQL_GET_CLIENT_BY_NAME", query = "SELECT c FROM ClientsEntity c WHERE c.name = :name"),
        @NamedQuery(name = "HQL_GET_CLIENT_BY_BALANCE", query = "SELECT c FROM ClientsEntity c WHERE c.balance = :balance")})
public class ClientsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "balance", nullable = false)
    private double balance;

    @OneToMany(mappedBy = "clientsByIdClient")
    @ToString.Exclude
    private List<PurchasesEntity> purchasesById;

    public ClientsEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientsEntity that = (ClientsEntity) o;
        return id == that.id && Double.compare(that.balance, balance) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance);
    }
}
