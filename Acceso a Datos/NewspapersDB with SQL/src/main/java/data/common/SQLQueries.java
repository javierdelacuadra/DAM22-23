package data.common;

public class SQLQueries {

    public static final String INSERT_READER = "INSERT INTO readers (id, name, dateOfBirth) VALUES (?,?,?)";
    public static final String DELETE_READER = "delete from readers where id = ?";
    public static final String UPDATE_READER = "update readers set name = ?, dateOfBirth = ? where id = ?";
    public static final String SELECT_READERS = "select * from readers";

    private SQLQueries() {
    }

}