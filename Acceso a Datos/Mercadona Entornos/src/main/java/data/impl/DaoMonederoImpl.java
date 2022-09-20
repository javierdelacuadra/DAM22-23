package data.impl;

import data.DaoMonedero;
import jakarta.inject.Inject;
import modelo.Cliente;
import modelo.Monedero;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DaoMonederoImpl implements DaoMonedero {

    private final Database db;

    @Inject
    public DaoMonederoImpl(Database db) {
        this.db = db;
    }

    @Override
    public boolean addSaldo(String dni, Monedero m) {
        Map<String, Cliente> clientes = db.loadClientes();
        Cliente c = clientes.get(dni);

        c.getMonederos().forEach(monedero -> {
            if (monedero.getNombre().equalsIgnoreCase(m.getNombre())) {
                monedero.setDinero(monedero.getDinero() + m.getDinero());
            }
        });
        return db.saveClientes(clientes);
    }

    @Override
    public boolean addMonedero(String dni, Monedero m) {
        Map<String, Cliente> clientes = db.loadClientes();
        Cliente c = clientes.get(dni);
        c.getMonederos().add(m);
        return db.saveClientes(clientes);
    }

    @Override
    public List<Monedero> verMonederos(String dni) {
        Map<String, Cliente> clientes = db.loadClientes();
        return clientes.get(dni).getMonederos().stream().collect(Collectors.toUnmodifiableList());
    }
}