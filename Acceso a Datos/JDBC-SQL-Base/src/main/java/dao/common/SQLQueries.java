package dao.common;

public class SQLQueries {
    public static final String INSERT_PURCHASE = "INSERT INTO purchases (id_client, p_date, total_cost, paid) VALUES (?, ?, ?, ?);";
    public static final String INSERT_PURCHASES_ITEMS = "INSERT INTO purchases_items (id_purchase, id_item, amount) VALUES (?, ?, ?);";
    public static final String SELECT_ITEM_PRICE_BY_ID = "SELECT price FROM items WHERE id = ?";
    public static final String SELECT_MOST_PURCHASED_PRODUCT = "SELECT items.id, items.name, items.price, purchases_items.amount from items, purchases_items where items.id = purchases_items.id_item";
    public static final String UPDATE_PURCHASED_ITEM = "UPDATE purchases_items SET amount = ? WHERE id = ?";
    public static final String SELECT_PURCHASE_ITEMS_BY_ID = "SELECT * from purchases_items WHERE id_purchase = ?";
    public static final String UPDATE_TOTAL_COST = "UPDATE purchases SET total_cost = ? WHERE id = ?";
    public static final String SELECT_CLIENT_BALANCE = "SELECT balance from clients WHERE id = ?";
    public static final String UPDATE_BALANCE = "UPDATE clients SET balance = ? WHERE id = ?";
    public static final String UPDATE_PURCHASE_STATUS = "UPDATE purchases SET paid = ? WHERE id = ?";
    public static final String SELECT_CLIENTS = "SELECT * from clients";
    public static final String SELECT_PURCHASES = "SELECT * from purchases";
}