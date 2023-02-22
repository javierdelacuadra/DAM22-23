package dao.common;

public class SQLQueries {

    public static final String SELECT_ALL_NEWSPAPERS = "SELECT * FROM newspapers ORDER BY id";
    public static final String SELECT_READER_BY_ID = "SELECT * FROM readers WHERE id = ?";

    public static final String SELECT_ARTICLES_BY_TYPE = "SELECT * FROM articles WHERE id_type = ? ORDER BY id";

    public static final String LOGIN_READER = "SELECT r.*, user, password FROM readers r INNER JOIN login l on r.id = l.id_reader WHERE user = ? AND password = ?";
    public static final String DELETE_FROM_SUBSCRIPTIONS_WHERE_ID_READER = "DELETE FROM subscriptions WHERE id_reader = ?";

    public static final String DELETE_FROM_ARTICLES = "DELETE FROM articles WHERE id = ?";
    public static final String SELECT_NEWSPAPERS_SUSCRIBED_READER = "SELECT n.* FROM newspapers n INNER JOIN subscriptions s ON n.id = s.id_newspaper WHERE s.id_reader = ? AND s.cancellation_date IS NULL";
    public static final String DELETE_FROM_READARTICLES_WHERE_ID_READER = "DELETE FROM readarticles WHERE id_reader = ?";
    public static final String DELETE_FROM_LOGIN_WHERE_ID_READER = "DELETE FROM login WHERE id_reader = ?";

    public static final String DELETE_FROM_READERS_BY_ID = "DELETE FROM readers WHERE id = ?";
    public static final String GET_OLDEST_SUSCRIPTORS = "SELECT name_reader FROM readers r INNER JOIN subscriptions s on r.id = s.id_reader WHERE s.id_newspaper = ? AND cancellation_date IS null ORDER BY signing_date LIMIT ?";
    public static final String UPDATE_LOGIN_SET = "UPDATE login SET user = ?, password = ? WHERE id_reader = ?";
    public static final String INSERT_INTO_LOGIN = "INSERT INTO login (user, password, id_reader) VALUES (?, ?, ?)";
    public static final String ADD_READ_ARTICLE = "INSERT INTO readarticles (id_reader, id_article, rating) VALUES (?, ?, ?)";

    public static final String UPDATE_NEWSPAPER_SET = "UPDATE newspapers SET name_newspaper = ?, release_date = ? WHERE id = ?";
    public static final String SELECT_UNREAD_ARTICLES_BY_READER_AND_NEWS = "SELECT * FROM articles WHERE id_newspaper = ? AND id NOT IN (SELECT id_article FROM readarticles WHERE id_reader = ?)";
    public static final String SELECT_ALL_ARTICLES = "SELECT * FROM articles ORDER BY id";

    public static final String SELECT_ALL_TYPES = "SELECT * FROM types ORDER BY id";

    private SQLQueries() {
    }


    public static final String SELECT_ALL_READERS_WITH_LOGIN = "SELECT r.*, user, password FROM readers r INNER JOIN login l on r.id = l.id_reader";



    public static final String INSERT_INTO_READERS = "INSERT INTO readers (name_reader, date_of_birth) VALUES (?, ?)";

    public static final String UPDATE_READERS_SET = "UPDATE readers SET name_reader = ?, date_of_birth = ? where id = ?";

    public static final String SELECT_ALL_READERS_BY_TYPE_NAME = "SELECT * FROM readers r INNER JOIN readarticles r2 on r.id = r2.id_reader" +
            "    INNER JOIN articles a on r2.id_article = a.id" +
            "    INNER JOIN types t on a.id_type = t.id" +
            "    WHERE t.name = ?";

    public static final String SELECT_ALL_READERS_BY_NEWSPAPER = "SELECT * FROM readers r INNER JOIN subscriptions s on r.id = s.id_reader" +
            "    INNER JOIN newspapers n on s.id_newspaper = n.id" +
            "    WHERE n.id = ? AND s.cancellation_date IS null";

    public static final String SELECT_READERS_AND_DESCRIPTION_BY_ARTICLE = "SELECT a.description, count(r.id_article) as cant_readers " +
            "FROM articles a LEFT JOIN readarticles r ON a.id = r.id_article " +
            "WHERE a.id = ? " +
            "GROUP BY a.id;";


    public static final String DELETE_FROM_NEWSPAPERS_BY_ID = "DELETE FROM newspapers WHERE id = ?";

    public static final String DELETE_READARTS_ARTS_IN_NEWS = "DELETE FROM readarticles WHERE id_article IN (SELECT id FROM articles WHERE id_newspaper = ?)";

    public static final String DELETE_ARTICLES_WHERE_ID_NEWS = "DELETE FROM articles WHERE id_newspaper = ?";

    public static final String DELETE_SUBSCRIPTIONS_WHERE_ID_NEWS = "DELETE FROM subscriptions WHERE id_newspaper = ?";

}
