package model;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@XmlRootElement(name = "client")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "balance")
    private double balance;

    @XmlElementWrapper(name = "purchases")
    @XmlElement(name = "purchase")
    private List<Purchase> purchases;

    public Client(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
}