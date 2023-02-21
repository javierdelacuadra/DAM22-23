package servicios.modelo.hibernate;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"purchasesItemsById"})

@Entity
@Table(name = "items", schema = "WebStore")

@NamedQueries({
        @NamedQuery(name = "HQL_GET_ITEMS_BY_ID", query = "from ItemsEntity n where n.id = :id")})
public class ItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;
    @OneToMany(mappedBy = "items")
    private Collection<PurchasesItemsEntity> purchasesItemsById;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemsEntity that = (ItemsEntity) o;
        return id == that.id && Double.compare(that.price, price) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    public ItemsEntity(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
