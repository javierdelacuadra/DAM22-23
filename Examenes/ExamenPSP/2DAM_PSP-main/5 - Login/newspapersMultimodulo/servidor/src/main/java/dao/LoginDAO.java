package dao;

import org.miutils.domain.modelo.Reader;

public interface LoginDAO {

    Reader login(String user, String password);

    int add(Reader reader);

    boolean validate(String token);

    boolean resetTime(String token);

    boolean validForgot(String email);

    boolean resetPass(String token, String generate);
}
