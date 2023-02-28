package org.example.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseMongo {
    private ObjectId _id;
    private LocalDate datePurchase;

    private ClientMongo client;

    private List<ItemMongo> items;

    public PurchaseMongo(ObjectId _id, List<ItemMongo> items) {
        this._id = _id;
        this.items = items;
    }
}
