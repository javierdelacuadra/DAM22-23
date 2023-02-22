package com.basicrest.springrestbasic.dao.common;

public class DaoConstants {

    private DaoConstants() {
    }
    public static final String ARTICLES = "articles";
    public static final String TYPE = "type";
    public static final String ID_NEWS = "id_news";
    public static final String NEWSPAPERS = "newspapers";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String RELEASE_DATE = "release_date";
    public static final String NEWSPAPER = "newspaper";
    public static final String UPDATE_ARTICLE = "update ArticleDTO n set n.name = ?1, n.type = ?2 where n.id = ?3";
    public static final String SELECT_ARTICLE_WHERE_ID = "SELECT a FROM ArticleDTO a WHERE a.id = ?1";
    public static final String UPDATE_NEWSPAPER = "update NewspaperDTO n set n.name = ?1, n.releaseDate = ?2 where n.id = ?3";
    public static final String GET_NEWSPAPER_WITH_ARTICLES_WHERE_ID = "SELECT n FROM NewspaperDTO n LEFT JOIN n.articles WHERE n.id = ?1";
}
