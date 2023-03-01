package servicios;

import dao.DaoTables;
import jakarta.inject.Inject;
import servicios.modelo.hibernate.TablesEntity;

public class ServicesTables {
    private final DaoTables daoTables;

    @Inject
    public ServicesTables(DaoTables daoTables) {
        this.daoTables = daoTables;
    }

    public TablesEntity getTableById(int tableID) {
        return daoTables.get(tableID);
    }
}
