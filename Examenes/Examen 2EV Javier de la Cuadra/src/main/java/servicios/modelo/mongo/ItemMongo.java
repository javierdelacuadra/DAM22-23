package servicios.modelo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItemMongo {
    private String name;
    private double price;
    private int amount;
}
