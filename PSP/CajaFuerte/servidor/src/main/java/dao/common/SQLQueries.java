package dao.common;

public class SQLQueries {

    public SQLQueries() {
    }

    public static final String SELECT_CARPETAS_BY_USUARIO = "SELECT * FROM carpeta WHERE IDUsuario = ?";
    public static final String INSERT_CARPETA = "INSERT INTO carpeta (nombreCarpeta, password, IDUsuario, modoEdicion) VALUES (?, ?, ?, ?)";
    public static final String SELECT_MENSAJES_BY_CARPETA = "SELECT mensajes.*,CASE WHEN carpeta.password = ? THEN 1 ELSE 0 END AS password_correcta FROM mensajes INNER JOIN carpeta ON mensajes.IDCarpeta = carpeta.id WHERE mensajes.IDCarpeta = ?;\n";
    public static final String INSERT_MENSAJE = "INSERT INTO mensajes (IDCarpeta, contenido) VALUES (?, ?)";
    public static final String UPDATE_MENSAJE = "UPDATE mensajes SET contenido = ? WHERE id = ?";
    public static final String DELETE_MENSAJE = "DELETE FROM mensajes WHERE ID = ?";
    public static final String SELECT_USER_BY_NAME = "SELECT * FROM usuarios WHERE nombre = ?";
    public static final String SELECT_USUARIOS = "SELECT * FROM usuarios";
    public static final String INSERT_USUARIO = "INSERT INTO usuarios (nombre, password, rol) VALUES (?, ?, ?)";
    public static final String SELECT_CARPETA_COMPARTIDA = "SELECT c.* FROM carpeta c INNER JOIN usuarios u ON c.IDUsuario = u.id WHERE c.nombreCarpeta = ? AND c.password = ? AND u.nombre = ?";

    public static final String UPDATE_CARPETA = "UPDATE carpeta SET password = ? WHERE id = ?";

    public static final String INSERT_READER = "INSERT INTO readers (name, date_of_birth) VALUES (?, ?)";
    public static final String INSERT_LOGIN = "insert into loginOscar (username, password, email, id_reader, activation_code, active, registration_date, role) values (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ACTIVATE_USER = "UPDATE loginOscar SET active = 1 WHERE activation_code = ? AND registration_date >= NOW() - INTERVAL 5 MINUTE;";
    public static final String SELECT_USER_BY_EMAIL = "select * from loginOscar where email = ?";
    public static final String UPDATE_REGISTER_DATE = "UPDATE loginOscar SET registration_date = ? WHERE email = ?";
    public static final String UPDATE_PASSWORD = "UPDATE loginOscar SET password = ? WHERE activation_code = ?";
}