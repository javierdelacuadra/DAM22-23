package domain.servicios;

import dao.DaoUsuarios;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import modelo.Usuario;

import java.util.List;

public class ServiciosUsuarios {

    private final DaoUsuarios dao;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServiciosUsuarios(DaoUsuarios dao, Pbkdf2PasswordHash passwordHash) {
        this.dao = dao;
        this.passwordHash = passwordHash;
    }

    public List<Usuario> getAllUsers() {
        return dao.getAll();
    }

    public Usuario addUsuario(Usuario usuario) {
        usuario.setPassword(passwordHash.generate(usuario.getPassword().toCharArray()));
        return dao.save(usuario);
    }

}
