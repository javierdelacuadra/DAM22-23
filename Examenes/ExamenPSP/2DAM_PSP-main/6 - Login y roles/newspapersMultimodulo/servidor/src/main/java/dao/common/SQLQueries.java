package dao.common;

public class SQLQueries {

    public static final String SELECT_ALL_NEWSPAPERS = "SELECT * FROM newspapers ORDER BY id";

    public static final String SELECT_ONE_NEWSPAPER = "SELECT * FROM newspapers WHERE id = ?";

    public static final String SELECT_READER_BY_ID = "SELECT * FROM readers WHERE id = ?";
    public static final String DELETE_FROM_SUBSCRIPTIONS_WHERE_ID_READER = "DELETE FROM subscriptions WHERE id_reader = ?";

    public static final String DELETE_FROM_READARTICLES_WHERE_ID_READER = "DELETE FROM readarticles WHERE id_reader = ?";
    public static final String DELETE_FROM_LOGIN_OSCAR_WHERE_ID_READER = "DELETE FROM login_oscar WHERE id_reader = ?";

    public static final String DELETE_FROM_READERS_BY_ID = "DELETE FROM readers WHERE id = ?";
    public static final String GET_TOP_5_OLDEST_SUSCRIPTOR = "SELECT name_reader FROM readers r INNER JOIN subscriptions s on r.id = s.id_reader WHERE s.id_newspaper = ? AND cancellation_date IS null ORDER BY signing_date LIMIT 5";
    public static final String INSERT_INTO_LOGIN = "INSERT INTO login_oscar (email, password, activated, activation_token, fecha_registro, id_reader) VALUES (?, ?, 0, ?, ?, ?)";

    public static final String UPDATE_NEWSPAPER_SET = "UPDATE newspapers SET name_newspaper = ?, release_date = ? WHERE id = ?";
    public static final String SELECT_ALL_TYPES = "SELECT * FROM types ORDER BY id";
    public static final String LOGIN_READER = "SELECT r.*, email, password, activated FROM readers r INNER JOIN login_oscar l on r.id = l.id_reader WHERE email = ?";
    public static final String GET_VALIDATED_FECHA_FROM_LOGIN = "SELECT activated, fecha_registro, email FROM login_oscar WHERE activation_token = ? LIMIT 1";

    public static final String GET_TOKEN_BY_EMAIL = "SELECT activation_token FROM login_oscar WHERE email = ?";
    public static final String SET_ACTIVATED_TRUE_WHERE_TOKEN = "UPDATE login_oscar SET activated = 1 WHERE email = ?";

    public static final String SET_FECHA_WHERE_TOKEN = "UPDATE login_oscar SET fecha_registro = ? WHERE email = ?";
    public static final String UPDATE_PASS_BY_TOKEN = "UPDATE login_oscar SET password = ? WHERE activation_token = ?";
    public static final String GET_ROLES_BY_ID_READER = "SELECT r.nombre FROM roles r INNER JOIN reader_roles rr on r.id = rr.role_id WHERE rr.reader_id = ?";
    public static final String INSERT_INTO_ROLES = "INSERT INTO reader_roles (reader_id, role_id) VALUES (?, ?)";
    public static final String DELETE_FROM_READERS_ROLES_BY_ID = "DELETE FROM reader_roles WHERE reader_id = ?";
    public static final String DELETE_FROM_LOGIN_WHERE_ID_READER = "DELETE FROM login WHERE id_reader = ?";


    private SQLQueries() {
    }


    public static final String SELECT_ALL_READERS_WITH_LOGIN = "SELECT r.*, email, password FROM readers r INNER JOIN login_oscar l on r.id = l.id_reader WHERE r.id > 0 ORDER BY r.id";


    public static final String INSERT_INTO_READERS = "INSERT INTO readers (name_reader, date_of_birth) VALUES (?, ?)";

    public static final String UPDATE_READERS_SET = "UPDATE readers SET name_reader = ?, date_of_birth = ? where id = ?";

    public static final String SELECT_ALL_READERS_BY_TYPE_NAME = "SELECT DISTINCT r.* FROM readers r INNER JOIN readarticles r2 on r.id = r2.id_reader" +
            "    INNER JOIN articles a on r2.id_article = a.id" +
            "    INNER JOIN types t on a.id_type = t.id" +
            "    WHERE t.name = ?";

    public static final String SELECT_ALL_READERS_BY_NEWSPAPER = "SELECT r.* FROM readers r INNER JOIN subscriptions s on r.id = s.id_reader" +
            "    INNER JOIN newspapers n on s.id_newspaper = n.id" +
            "    WHERE n.id = ? AND s.cancellation_date IS null";

    public static final String DELETE_FROM_NEWSPAPERS_BY_ID = "DELETE FROM newspapers WHERE id = ?";

    public static final String DELETE_READARTS_ARTS_IN_NEWS = "DELETE FROM readarticles WHERE id_article IN (SELECT id FROM articles WHERE id_newspaper = ?)";

    public static final String DELETE_ARTICLES_WHERE_ID_NEWS = "DELETE FROM articles WHERE id_newspaper = ?";

    public static final String DELETE_SUBSCRIPTIONS_WHERE_ID_NEWS = "DELETE FROM subscriptions WHERE id_newspaper = ?";

}
