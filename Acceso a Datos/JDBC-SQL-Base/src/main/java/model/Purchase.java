package model;

import di.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "purchase")
@XmlAccessorType(XmlAccessType.FIELD)
public class Purchase {

    @XmlElement(name = "idPurchase")
    public int id;

    @XmlElement(name = "idClient")
    public int id_client;

    @XmlElement(name = "pDate")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate p_date;

    @XmlElement(name = "totalCost")
    public double total_cost;

    @XmlElement(name = "isPaid")
    public int paid;

    @XmlElementWrapper(name = "purchasedItems")
    @XmlElement(name = "purchasedItem")
    public List<PurchasesItem> purchases;
}