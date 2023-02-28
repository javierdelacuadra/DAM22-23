package org.example.model.hibernate;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.common.Queries;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "items")
@NamedQuery(name = "HQL_ALL_ITEMS", query = Queries.FROM_ITEM)
@NamedQuery(name = "HQL_ALL_ITEMS_BOUGHT_BY_A_CLIENT", query = Queries.ANNES_ITEMS)
@NamedQuery(name = "HQL_ITEM_BY_NAME", query = Queries.FROM_ITEM_WHERE_NAME)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    public Item(int id) {
        this.id = id;
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id || name.equalsIgnoreCase(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
