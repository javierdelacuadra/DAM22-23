package domain.servicios;

import jakarta.mail.MessagingException;
import org.miutils.domain.modelo.Reader;

import java.util.List;

public interface LoginServices {


    Reader login(String email, String password);

    Reader register(Reader reader);

    boolean validate(String token);

    boolean resetTime(String token) throws MessagingException;

    boolean forgot(String email);

    boolean resetPass(String token, String pass);

    List<String> getRoles(int id);
}
