package domain.servicios;

import domain.modelo.Reader;

public interface LoginServices {


    Reader login(String user, String password);
}
