package ui.screens.articles.addArticle;

import lombok.Data;
import model.Article;
import model.Newspaper;
import model.QueryDescAndReaders;
import model.TypeArt;

import java.util.List;

@Data
public class AddArticleState {

    private String error;
    private
    List<Article> articlesList;
    private List<Newspaper> newspaperList;
    private List<TypeArt> typeArtList;

    private String confirmation;

    public AddArticleState(String error, List<Article> articlesList, List<Newspaper> newspaperList, List<TypeArt> typeArtList,  String confirmation) {
        this.error = error;
        this.articlesList = articlesList;
        this.newspaperList = newspaperList;
        this.typeArtList = typeArtList;
        this.confirmation = confirmation;
    }


}
