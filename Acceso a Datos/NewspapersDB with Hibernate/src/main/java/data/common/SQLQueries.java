package data.common;

public class SQLQueries {

    public static final String SELECT_ARTICLE_TYPE_ARTICLE_NAME_AND_READERS = "select type.description, article.name_article, count(readarticle.id_reader) from type, article, readarticle where type.id = article.id_type and article.id = readarticle.id_article group by type.description, article.name_article";
    public static final String SELECT_OLDEST_SUBSCRIBERS = "select * from readers where id in (select id_reader from subscription where id_newspaper in (select id from newspaper where name = 'El Hola Mundo') order by start_date)";
    public static final String SELECT_ARTICLES_BY_TYPE_AND_NEWSPAPER = "select article.id, article.name_article, article.id_type, newspaper.name from article, newspaper where article.id_newspaper = newspaper.id and newspaper.name = ? and article.id_type in (select id from type where description = ?)";
    public static final String SELECT_ARTICLES_BY_NEWSPAPER_AND_BAD_RATINGS = "select a.id, a.name_article, r.id_reader, r.rating, (select count(*) from readarticle where id_reader = r.id_reader and rating < 5) as bad_ratings from article a left join readarticle r on a.id = r.id_article where a.id_newspaper = ? and r.rating < 5";

    private SQLQueries() {
    }
}