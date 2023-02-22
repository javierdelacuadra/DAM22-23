package dao;

import org.miutils.domain.modelo.Reader;

import java.util.List;

public interface LoginDAO {

    Reader login(String user, String password);

    String add(Reader reader);

    boolean validate(String token);

    String resetTime(String token);

    String validForgot(String email);

    boolean resetPass(String token, String generate);

    List<String> getRoles(int id);

    boolean changePetitonsStatus(String email);
}
