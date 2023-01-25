package dao;

import dao.common.Constantes;
import dao.common.SQLQueries;
import domain.exceptions.DatabaseException;
import domain.exceptions.ObjectAlreadyExistsException;
import domain.exceptions.ObjectNotFoundException;
import jakarta.inject.Inject;
import modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUsuarios {

    private final DBConnection db;

    @Inject
    public DaoUsuarios(DBConnection db) {
        this.db = db;
    }

    public List<Usuario> getAll() {
        List<Usuario> usuarios;
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_USUARIOS);
            usuarios = readRS(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            throw new ObjectNotFoundException(Constantes.NO_SE_HAN_ENCONTRADO_READERS);
        }
        return usuarios;
    }

    public Usuario save(Usuario usuario) {
        List<Usuario> usuarios = getAll();
        if (usuarios.stream().noneMatch(r -> r.getNombre().equals(usuario.getNombre()))) {
            try (Connection con = db.getConnection();
                 PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_USUARIO, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, usuario.getNombre());
                preparedStatement.setString(2, usuario.getPassword());
                preparedStatement.setString(3, "user");
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                    usuario.setRol("user");
                }
                return usuario;
            } catch (SQLException ex) {
                Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                throw new DatabaseException(Constantes.NO_SE_HA_PODIDO_GUARDAR_EL_READER);
            }
        }
        throw new ObjectAlreadyExistsException(Constantes.YA_EXISTE_UN_READER_CON_ESE_NOMBRE);
    }

    public boolean delete(String id) {
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_READER)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, e);
            throw new DatabaseException(Constantes.NO_SE_HA_PODIDO_ELIMINAR_EL_READER);
        }
    }

    private List<Usuario> readRS(ResultSet rs) {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt(Constantes.ID));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(rs.getString("rol"));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }
}