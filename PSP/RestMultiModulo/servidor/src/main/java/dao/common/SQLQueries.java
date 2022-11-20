package dao.common;

public class SQLQueries {
    public static final String SELECT_READERS = "SELECT * FROM readers";
    public static final String UPDATE_READER = "UPDATE readers SET name = ?, date_of_birth = ? WHERE id = ?";
    public static final String DELETE_READER = "DELETE FROM readers WHERE id = ?";
    public static final String INSERT_READER = "INSERT INTO readers (name, date_of_birth) VALUES (?, ?)";
    public static final String SELECT_READER_BY_ID = "SELECT * FROM readers WHERE id = ?";
    public static final String SELECT_NEWSPAPERS = "SELECT * FROM newspaper";
    public static final String INSERT_NEWSPAPER = "INSERT INTO newspaper (name, release_date) VALUES (?, ?)";
    public static final String UPDATE_NEWSPAPER = "UPDATE newspaper SET name = ?, release_date = ? WHERE id = ?";
    public static final String DELETE_NEWSPAPER = "DELETE FROM newspaper WHERE id = ?";
    public static final String SELECT_NEWSPAPER_BY_ID = "SELECT * FROM newspaper WHERE id = ?";
    public static final String SELECT_ARTICLES = "SELECT * FROM article";
    public static final String INSERT_ARTICLE = "INSERT INTO article (name_article, id_type, id_newspaper) VALUES (?, ?, ?)";
    public static final String UPDATE_ARTICLE = "UPDATE article SET name_article = ?, id_type = ?, id_newspaper = ? WHERE id = ?";
    public static final String DELETE_ARTICLE = "DELETE FROM article WHERE id = ?";
    public static final String SELECT_ARTICLE_BY_ID = "SELECT * FROM article WHERE id = ?";
    public static final String INSERT_SUBSCRIPTION = "INSERT INTO subscription (id_newspaper, id_reader, start_date, cancellation_date) VALUES (?, ?, ?, ?)";
    public static final String DELETE_SUBSCRIPTION = "delete from subscription where id_newspaper = ? and id_reader = ?";
    public static final String SELECT_ARTICLE_TYPE_ARTICLE_NAME_AND_READERS = "select type.description, article.name_article, count(readarticle.id_reader) from type, article, readarticle where type.id = article.id_type and article.id = readarticle.id_article group by type.description, article.name_article";
    public static final String INSERT_READ_ARTICLE = "INSERT INTO readarticle (id_article, id_reader, rating) VALUES (?, ?, ?)";
    public static final String SELECT_READARTICLES_BY_ID_ARTICLE = "select * from readarticle where id_article = ?";
    public static final String SELECT_OLDEST_SUBSCRIBERS = "select * from readers where id in (select id_reader from subscription where id_newspaper in (select id from newspaper where name = 'El Hola Mundo') order by start_date)";
    public static final String SELECT_READERS_BY_ARTICLE_TYPE = "select * from readers where id in (select id_reader from readarticle where id_article in (select id from article where id_type in (select id from type where description = ?)))";
    public static final String SELECT_READERS_BY_NEWSPAPER = "select * from readers where id in (select id_reader from subscription where id_newspaper = ?)";
    public static final String SELECT_ARTICLES_BY_TYPE = "select * from article where id_type in (select id from type where description = ?)";
    public static final String SELECT_ARTICLES_BY_TYPE_AND_NEWSPAPER = "select article.id, article.name_article, article.id_type, newspaper.name from article, newspaper where article.id_newspaper = newspaper.id and article.id_type in (select id from type where description = ?)";
    public static final String SELECT_ARTICLES_BY_NEWSPAPER_AND_BAD_RATINGS = "select a.id, a.name_article, r.id_reader, r.rating, (select count(*) from readarticle where id_reader = r.id_reader and rating < 5) as bad_ratings from article a left join readarticle r on a.id = r.id_article where a.id_newspaper = ? and r.rating < 5";
}