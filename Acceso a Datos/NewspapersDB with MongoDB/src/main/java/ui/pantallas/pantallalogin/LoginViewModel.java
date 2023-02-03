package ui.pantallas.pantallalogin;

import jakarta.inject.Inject;
import model.Login;
import model.Reader;
import servicios.ServicesLogin;
import servicios.ServicesReadersSQL;

public class LoginViewModel {

    private final ServicesReadersSQL servicesReadersSQL;
    private final ServicesLogin servicesLogin;

    @Inject
    public LoginViewModel(ServicesReadersSQL servicesReadersSQL, ServicesLogin servicesLogin) {
        this.servicesReadersSQL = servicesReadersSQL;
        this.servicesLogin = servicesLogin;
    }

    public Integer login(Login login) {
        return servicesLogin.login(login);
    }

    public Reader getReader(Integer id) {
        return servicesReadersSQL.getReadersById(id);
    }
}
