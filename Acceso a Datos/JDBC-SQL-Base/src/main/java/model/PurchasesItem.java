package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "purchasedItems")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchasesItem {
    public int id;
    public int id_purchase;
    public int id_item;
    public int amount;
}
