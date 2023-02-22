package dao;

import domain.modelo.Reader;

public interface LoginDAO {

    Reader login(String user, String password);
}
