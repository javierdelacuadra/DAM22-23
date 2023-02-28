package org.example.model.common;

public class Queries {

    public static final String FROM_CLIENTS = "from Client";

    public static final String FROM_PURCHASE = "from Purchase order by id";

    public static final String FROM_ITEM = "from Item";
    public static final String ANNES_ITEMS = "select distinct p.item from Purchases_items p where p.purchase.client.name = :clientName";
    public static final String FROM_CLIENTS_WHERE_NAME = "from Client where name = :clientName";
    public static final String FROM_ITEM_WHERE_NAME = "from Item where name = :itemName";
    public static final String DELETE_BY_NAME = "delete from Client c where c.name = :clientName";
    public static final String FROM_PURCHASES_ITEMS_PI_WHERE_PI_PURCHASE_CLIENT_NAME_NAME_CLIENT = "select id from Purchases_items pi where pi.purchase.client.name = :nameClient";
    public static final String DELETE_PP_BY_ID = "delete from Purchases_items where id in (:listId)";
}
