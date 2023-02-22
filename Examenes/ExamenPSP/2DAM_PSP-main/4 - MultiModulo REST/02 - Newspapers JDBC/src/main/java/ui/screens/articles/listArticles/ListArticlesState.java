package ui.screens.articles.listArticles;

import lombok.Data;
import model.Article;
import model.QueryArticlesAndNews;
import model.QueryDescAndReaders;
import model.TypeArt;

import java.util.List;

@Data
public class ListArticlesState {

    private String error;
    private List<Article> articles;

    private QueryDescAndReaders queryDescAndReaders;
    private List<TypeArt> typeArts;
    private List<Article> articlesFiltered;

    private List<QueryArticlesAndNews> queryArticlesAndNewsList;

    public ListArticlesState(String error, List<Article> articles, QueryDescAndReaders queryDescAndReaders, List<TypeArt> typeArts, List<Article> articlesFiltered, List<QueryArticlesAndNews> queryArticlesAndNewsList) {
        this.error = error;
        this.articles = articles;
        this.queryDescAndReaders = queryDescAndReaders;
        this.typeArts = typeArts;
        this.articlesFiltered = articlesFiltered;
        this.queryArticlesAndNewsList = queryArticlesAndNewsList;
    }

}
