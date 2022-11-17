package data.common;

public class SQLQueries {

    public static final String SELECT_READERS = "select * from readers where id > 0";
    public static final String INSERT_READER = "INSERT INTO readers (name, dateOfBirth) VALUES (?,?)";
    public static final String INSERT_LOGIN = "INSERT INTO login (name, password, id_reader) VALUES (?, ?, ?)";
    public static final String DELETE_READER = "delete from readers where id = ?";
    public static final String DELETE_FROM_LOGIN = "delete from login where name = ?";
    public static final String DELETE_FROM_READARTICLES = "delete from readarticle where id_reader = ?";
    public static final String DELETE_FROM_SUBSCRIPTIONS = "delete from subscription where id_reader = ?";
    public static final String UPDATE_READER_LOGIN = "update login set name = ? where id_reader = ?";
    public static final String UPDATE_READER = "update readers set name = ?, dateOfBirth = ? where id = ?";
    public static final String SELECT_READERS_BY_NEWSPAPER = "select * from readers where id in (select id_reader from subscription where id_newspaper = ?)";
    public static final String LOGIN = "select * from login where name = ? and password = ?";
    public static final String SELECT_READER_BY_NAME = "select id from readers where name = ?";
    public static final String SELECT_READER_BY_ID = "select * from readers where id = ?";
    public static final String SELECT_READERS_BY_ARTICLE_TYPE = "select * from readers where id in (select id_reader from readarticle where id_article in (select id from article where id_type in (select id from type where description = ?)))";
    public static final String SELECT_ARTICLE_TYPE = "select * from type";
    public static final String SELECT_ARTICLES = "select * from article";
    public static final String INSERT_READ_ARTICLE = "INSERT INTO readarticle (id_article, id_reader, rating) VALUES (?, ?, ?)";
    public static final String SELECT_ARTICLES_BY_READER = "select * from article where id_newspaper in (select id_newspaper from subscription where id_reader = ?)";
    public static final String SELECT_NEWSPAPERS = "select * from newspaper";
    public static final String INSERT_SUBSCRIPTION = "INSERT INTO subscription (id_newspaper, id_reader, start_date, cancellation_date) VALUES (?, ?, ?, ?)";
    public static final String DELETE_SUBSCRIPTION = "delete from subscription where id_newspaper = ? and id_reader = ?";
    public static final String SELECT_ARTICLE_TYPE_ARTICLE_NAME_AND_READERS = "select type.description, article.name_article, count(readarticle.id_reader) from type, article, readarticle where type.id = article.id_type and article.id = readarticle.id_article group by type.description, article.name_article";
    public static final String SELECT_OLDEST_SUBSCRIBERS = "select * from readers where id in (select id_reader from subscription where id_newspaper in (select id from newspaper where name = 'El Hola Mundo') order by start_date)";
    public static final String SELECT_READARTICLES_BY_ID_ARTICLE = "select * from readarticle where id_article = ? and id_reader = ?";
    public static final String INSERT_NEWSPAPER = "INSERT INTO newspaper (name, release_date) VALUES (?, ?)";
    public static final String DELETE_NEWSPAPER = "delete from newspaper where id = ?";
    public static final String UPDATE_NEWSPAPER = "update newspaper set name = ?, release_date = ? where id = ?";
    public static final String SELECT_ARTICLES_BY_TYPE_AND_NEWSPAPER = "select article.id, article.name_article, article.id_type, newspaper.name from article, newspaper where article.id_newspaper = newspaper.id and newspaper.name = ? and article.id_type in (select id from type where description = ?)";
    public static final String SELECT_ARTICLES_BY_NEWSPAPER_AND_BAD_RATINGS = "select a.id, a.name_article, r.id_reader, r.rating, (select count(*) from readarticle where id_reader = r.id_reader and rating < 5) as bad_ratings from article a left join readarticle r on a.id = r.id_article where a.id_newspaper = ? and r.rating < 5";
    public static final String INSERT_ARTICLE = "INSERT INTO article (name_article, id_type, id_newspaper) VALUES (?, ?, ?)";
    public static final String SELECT_ARTICLES_BY_TYPE = "select * from article where id_type in (select id from type where description = ?)";

    private SQLQueries() {
    }
}