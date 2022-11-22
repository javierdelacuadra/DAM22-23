package dao.common;

public class SQLQueries {
    public static final String SELECT_OBJETOS = "SELECT * FROM objetos";
    public static final String INSERT_OBJETO = "INSERT INTO objetos (nombre, apellido, fecha) VALUES (?, ?, ?)";
    public static final String UPDATE_OBJETO = "UPDATE objetos SET nombre = ?, apellido = ?, fecha = ? WHERE id = ?";
    public static final String DELETE_OBJETO = "DELETE FROM objetos WHERE id = ?";
    public static final String SELECT_OBJETO = "SELECT * FROM objetos WHERE id = ?";
}