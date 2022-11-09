package dao.common;

public class SQLQueries {
    public static final String SELECT_READERS = "SELECT * FROM readers";
    public static final String INSERT_READER = "INSERT INTO readers (name, date_of_birth) VALUES (?, ?)";
    public static final String UPDATE_READER = "UPDATE readers SET name = ?, date_of_birth = ? WHERE id = ?";
    public static final String DELETE_READER = "DELETE FROM readers WHERE id = ?";
    public static final String SELECT_READER_BY_ID = "SELECT * FROM readers WHERE id = ?";
}
