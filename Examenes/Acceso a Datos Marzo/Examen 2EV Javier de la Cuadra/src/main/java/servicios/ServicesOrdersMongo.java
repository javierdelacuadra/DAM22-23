package servicios;

import dao.DaoOrdersMongo;
import jakarta.inject.Inject;
import servicios.modelo.hibernate.OrderItemsEntity;
import servicios.modelo.hibernate.OrdersEntity;
import servicios.modelo.mongo.CustomerMongo;
import servicios.modelo.mongo.MenuItemMongo;
import servicios.modelo.mongo.OrderMongo;

import java.util.ArrayList;
import java.util.List;

public class ServicesOrdersMongo {
    private final DaoOrdersMongo daoOrdersMongo;

    @Inject
    public ServicesOrdersMongo(DaoOrdersMongo daoOrdersMongo) {
        this.daoOrdersMongo = daoOrdersMongo;
    }

    public Integer addAllOrders(List<OrdersEntity> orders) {
        List<OrderMongo> ordersMongo = new ArrayList<>();
        for (OrdersEntity order : orders) {
            CustomerMongo customerMongo = new CustomerMongo(order.getCustomer().getFirstName(), order.getCustomer().getLastName(), order.getCustomer().getEmail());
            OrderMongo newOrder = new OrderMongo(String.valueOf(order.getOrderDate()), customerMongo, new ArrayList<>());
            for (OrderItemsEntity orderItem : order.getOrderItems()) {
                MenuItemMongo menuItem = new MenuItemMongo(orderItem.getMenuItem().getName(), orderItem.getPrice(), orderItem.getQuantity());
                newOrder.getItems().add(menuItem);
            }
            ordersMongo.add(newOrder);
        }
        return daoOrdersMongo.add(ordersMongo);
    }
}
