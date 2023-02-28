package org.example.model.hibernate;


import jakarta.persistence.*;
import lombok.*;
import org.example.model.common.Queries;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "clients")
@NamedQuery(name ="HQL_ALL_CLIENTS", query = Queries.FROM_CLIENTS)
@NamedQuery(name ="HQL_GET_CLIENT_BY_NAME", query = Queries.FROM_CLIENTS_WHERE_NAME)
@NamedQuery(name = "HQL_DELETE_CLIENT_BY_NAME", query = Queries.DELETE_BY_NAME)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private double balance;

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Purchase> purchases;

    //simple client for the purchase object
    public Client(int id) {
        this.id = id;
    }

    public Client(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id || Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
