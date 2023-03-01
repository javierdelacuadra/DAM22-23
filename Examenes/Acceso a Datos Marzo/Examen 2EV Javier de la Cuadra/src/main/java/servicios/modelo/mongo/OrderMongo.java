package servicios.modelo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderMongo {
    private ObjectId _id;
    private String orderDate;
    private CustomerMongo customer;
    private List<MenuItemMongo> items;

    public OrderMongo(String orderDate, CustomerMongo customer, List<MenuItemMongo> items) {
        this.orderDate = orderDate;
        this.customer = customer;
        this.items = items;
    }

    public OrderMongo(String orderDate, CustomerMongo customer) {
        this.orderDate = orderDate;
        this.customer = customer;
    }
}
