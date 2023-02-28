package servicios.modelo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseMongo {
    private ObjectId _id;
    private String datePurchase;
    private ClientMongo client;
    private List<ItemMongo> items;

    public PurchaseMongo(ObjectId _id) {
        this._id = _id;
    }
}
