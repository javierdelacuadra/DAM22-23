package data.common;

public class SQLQueries {

    public static final String SELECT_READERS = "select * from readers";
    public static final String INSERT_READER = "INSERT INTO readers (name, dateOfBirth) VALUES (?,?)";
    public static final String DELETE_READER = "delete from readers where id = ?";
    public static final String UPDATE_READER = "update readers set name = ?, dateOfBirth = ? where id = ?";
    public static final String SELECT_READERS_BY_NEWSPAPER = "select * from readers where id in (select id_reader from subscription where id_newspaper = ?)";
    public static final String LOGIN = "select * from login where name = ? and password = ?";
    public static final String SELECT_READER_BY_NAME = "select id from readers where name = ?";
    public static final String SELECT_READER_BY_ID = "select * from readers where id = ?";
    public static final String SELECT_READERS_BY_ARTICLE_TYPE = "select * from readers where id in (select id_reader from readarticle where id_article in (select id from article where id_type in (select id from type where description = ?)))";
    public static final String SELECT_ARTICLE_TYPE = "select * from type";
    public static final String SELECT_ARTICLES = "select * from article";
    public static final String INSERT_READ_ARTICLE = "INSERT INTO readarticle (id_article, id_reader, rating) VALUES (?, ?, ?)";
    //select all the articles of the newspapers that the reader is subscribed to
    public static final String SELECT_ARTICLES_BY_READER = "select * from article where id_newspaper in (select id_newspaper from subscription where id_reader = ?)";
    public static final String SELECT_NEWSPAPERS = "select * from newspaper";

    private SQLQueries() {
    }
}