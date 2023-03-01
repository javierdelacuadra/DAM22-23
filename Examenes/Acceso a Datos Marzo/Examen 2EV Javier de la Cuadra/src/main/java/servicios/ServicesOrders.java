package servicios;

import dao.DaoOrders;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import servicios.modelo.hibernate.OrdersEntity;

import java.util.List;

public class ServicesOrders {
    private final DaoOrders daoOrders;

    @Inject
    public ServicesOrders(DaoOrders daoOrders) {
        this.daoOrders = daoOrders;
    }

    public Integer addOrder(OrdersEntity purchase) {
        return daoOrders.add(purchase);
    }

    public Either<Integer, List<OrdersEntity>> getAllOrders() {
        return daoOrders.getAll();
    }
}
