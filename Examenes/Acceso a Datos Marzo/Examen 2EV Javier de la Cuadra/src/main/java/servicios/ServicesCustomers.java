package servicios;

import dao.DaoCustomers;
import dao.DaoCustomersMongo;
import jakarta.inject.Inject;
import servicios.modelo.hibernate.CustomersEntity;

import java.util.List;

public class ServicesCustomers {
    private final DaoCustomers dao;
    private final DaoCustomersMongo daoMongo;

    @Inject
    public ServicesCustomers(DaoCustomers dao, DaoCustomersMongo daoMongo) {
        this.dao = dao;
        this.daoMongo = daoMongo;
    }

    public CustomersEntity getCustomerByEmail(String email) {
        return dao.get(email);
    }

    public Integer deleteAllCustomerData(CustomersEntity customer, boolean delete) {
        return dao.delete(customer, delete);
    }

    public List<String> aggregation() {
        return daoMongo.aggregation();
    }

    public CustomersEntity getCustomerByName(String firstName, String lastName) {
        return dao.get(firstName, lastName);
    }
}
