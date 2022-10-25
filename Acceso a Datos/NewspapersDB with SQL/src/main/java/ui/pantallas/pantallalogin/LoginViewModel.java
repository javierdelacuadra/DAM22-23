package ui.pantallas.pantallalogin;

import jakarta.inject.Inject;
import model.Reader;
import servicios.ServicesReadersSQL;

public class LoginViewModel {

    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public LoginViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public Integer login(String nombre, String password) {
        return servicesReadersSQL.login(nombre, password);
    }

    public Reader getReader(int id) {
        return servicesReadersSQL.getReadersById(id);
    }
}
