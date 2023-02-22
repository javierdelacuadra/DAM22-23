package dao.common;

public class SQLQueries {

    public static final String SELECT_ALL_NEWSPAPERS = "SELECT * FROM newspapers ORDER BY id";

    public static final String SELECT_ONE_NEWSPAPER = "SELECT * FROM newspapers WHERE id = ?";

    public static final String SELECT_READER_BY_ID = "SELECT * FROM readers WHERE id = ?";

    public static final String SELECT_ARTICLES_BY_TYPE = "SELECT * FROM articles WHERE id_type = ? ORDER BY id";

    public static final String LOGIN_READER = "SELECT r.*, user, password FROM readers r INNER JOIN login l on r.id = l.id_reader WHERE user = ? AND password = ?";
    public static final String DELETE_FROM_SUBSCRIPTIONS_WHERE_ID_READER = "DELETE FROM subscriptions WHERE id_reader = ?";

    public static final String SELECT_NEWSPAPERS_SUSCRIBED_READER = "SELECT n.* FROM newspapers n INNER JOIN subscriptions s ON n.id = s.id_newspaper WHERE s.id_reader = ? AND s.cancellation_date IS NULL";
    public static final String SELECT_ALL_NEWSPAPERS_UNSUSCRIBED = "SELECT n.* FROM newspapers n WHERE n.id NOT IN (SELECT newspapers.id FROM newspapers JOIN subscriptions s ON newspapers.id = s.id_newspaper WHERE s.id_reader = ? AND s.cancellation_date IS NULL) ORDER BY id";
    public static final String DELETE_FROM_READARTICLES_WHERE_ID_READER = "DELETE FROM readarticles WHERE id_reader = ?";
    public static final String DELETE_FROM_LOGIN_WHERE_ID_READER = "DELETE FROM login WHERE id_reader = ?";

    public static final String DELETE_FROM_READERS_BY_ID = "DELETE FROM readers WHERE id = ?";
    public static final String GET_TOP_5_OLDEST_SUSCRIPTOR = "SELECT name_reader FROM readers r INNER JOIN subscriptions s on r.id = s.id_reader WHERE s.id_newspaper = ? AND cancellation_date IS null ORDER BY signing_date LIMIT 5";
    public static final String UPDATE_LOGIN_SET = "UPDATE login SET user = ?, password = ? WHERE id_reader = ?";
    public static final String INSERT_INTO_LOGIN = "INSERT INTO login (user, password, id_reader) VALUES (?, ?, ?)";
    public static final String ADD_READ_ARTICLE = "INSERT INTO readarticles (id_reader, id_article, rating) VALUES (?, ?, ?)";

    public static final String UPDATE_NEWSPAPER_SET = "UPDATE newspapers SET name_newspaper = ?, release_date = ? WHERE id = ?";
    public static final String SELECT_ALL_ARTICLES = "SELECT * FROM articles ORDER BY id";

    public static final String SELECT_ARTICLES_BY_NEWS = "SELECT * FROM articles WHERE id_newspaper = ?";

    public static final String SELECT_ALL_TYPES = "SELECT * FROM types ORDER BY id";
    public static final String SELECT_ARTNAME_AND_NEWSNAME = "SELECT name_article, name_newspaper FROM articles " +
            "    INNER JOIN newspapers n ON articles.id_newspaper = n.id " +
            "    INNER JOIN types t ON articles.id_type = t.id " +
            "    WHERE t.id = ?";
    public static final String IMPOSSIBLE_QUERY = """
            SELECT
                        
                   a.name_article,
                        
                   (SELECT COUNT(*)
                        
                    FROM readarticles
                        
                    WHERE id_reader = r.id_reader
                        
                      AND rating < 3) AS bad_ratings
                        
            FROM articles a
                        
                     LEFT JOIN readarticles r
                               ON a.id = r.id_article
                        
            WHERE a.id_newspaper = ?
                        
              AND r.rating < 3""";

    public static final String UPDATE_READARTICLES_SET_RATING_WHERE_ID_READER_AND_ID_ARTICLE = "UPDATE readarticles SET rating = ? WHERE id_reader = ? AND id_article = ?";
    public static final String SELECT_ID_FROM_READARTICLES_WHERE_ID_READER_AND_ID_ARTICLE = "SELECT id FROM readarticles WHERE id_reader = ? AND id_article = ?";
    public static final String SELECT_FROM_READARTICLES = "SELECT * FROM readarticles";
    public static final String UPDATE_SUBSCRIPTIONS_SET_CANCELLATION_DATE_WHERE_ID_READER_AND_ID_NEWSPAPER = "UPDATE subscriptions SET cancellation_date = ? WHERE id_reader = ? AND id_newspaper = ? AND signing_date = ?";
    public static final String UPDATE_SUBSCRIPTIONS_SET_CANCELLATION_DATE_NULL_SIGNING_DATE_WHERE_ID_READER_AND_ID_NEWSPAPER = "UPDATE subscriptions SET cancellation_date = null, signing_date = ? WHERE id_reader = ? AND id_newspaper = ?";

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

    public static final String SELECT_RATING_FROM_READARTICLES = "SELECT rating FROM readarticles r WHERE r.id_reader = ? AND r.id_article = ?";


}
