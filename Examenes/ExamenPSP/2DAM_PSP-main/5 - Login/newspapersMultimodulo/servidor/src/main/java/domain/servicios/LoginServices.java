package domain.servicios;

import org.miutils.domain.modelo.Reader;

public interface LoginServices {


    Reader login(String email, String password);

    Reader register(Reader reader);

    boolean validate(String token);

    boolean resetTime(String token);

    boolean forgot(String email);

    boolean resetPass(String token, String pass);
}
