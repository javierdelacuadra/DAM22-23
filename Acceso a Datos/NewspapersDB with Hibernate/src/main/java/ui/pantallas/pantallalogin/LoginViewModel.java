package ui.pantallas.pantallalogin;

import jakarta.inject.Inject;
import model.Login;
import model.Reader;
import servicios.ServicesReadersSQL;

public class LoginViewModel {

    private final ServicesReadersSQL servicesReadersSQL;

    @Inject
    public LoginViewModel(ServicesReadersSQL servicesReadersSQL) {
        this.servicesReadersSQL = servicesReadersSQL;
    }

    public Integer login(Login login) {
        return servicesReadersSQL.login(login);
    }

    public Reader getReader(int id) {
        return servicesReadersSQL.getReadersById(id);
    }
}
