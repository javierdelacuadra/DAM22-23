package ui.screens.articles.addArticle;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Article;
import model.Newspaper;
import model.TypeArt;
import serivces.impl.ArticleServicesImpl;
import serivces.impl.NewspapersServicesImpl;
import serivces.impl.TypeServicesImpl;
import ui.screens.common.ScreenConstants;

import java.util.List;

public class AddArticleViewModel {

    NewspapersServicesImpl newspapersServicesImpl;
    ArticleServicesImpl articleServicesImpl;

    TypeServicesImpl typeServicesImpl;

    @Inject
    public AddArticleViewModel(NewspapersServicesImpl newspapersServicesImpl, ArticleServicesImpl articleServicesImpl, TypeServicesImpl typeServicesImpl) {
        this.newspapersServicesImpl = newspapersServicesImpl;
        this.articleServicesImpl = articleServicesImpl;
        this.typeServicesImpl = typeServicesImpl;
        _state = new SimpleObjectProperty<>(new AddArticleState(null, null, null, null, null));
    }

    private final ObjectProperty<AddArticleState> _state;

    public ReadOnlyObjectProperty<AddArticleState> getState() {
        return _state;
    }

    public void loadScreen() {
        Either<String, List<Newspaper>> newspaperList = newspapersServicesImpl.getAll();
        AddArticleState jn;
        if (newspaperList.isRight()) {
            Either<String, List<Article>> articlesByPaper = articleServicesImpl.getArticlesByNewspaper(newspaperList.get().get(0));
            if (articlesByPaper.isRight()) {
                Either<String, List<TypeArt>> typeArtList = typeServicesImpl.getAllTypes();
                if (typeArtList.isRight()) {
                    jn = new AddArticleState(null, articlesByPaper.get(), newspaperList.get(), typeArtList.get(), null);
                } else {
                    jn = new AddArticleState(typeArtList.getLeft(), null, null, null, null);
                }
            } else {
                jn = new AddArticleState(articlesByPaper.getLeft(), null, null, null, null);
            }
        } else {
            jn = new AddArticleState(newspaperList.getLeft(), null, null, null, null);
        }
        _state.setValue(jn);
    }

    public void updateArtTable(int index) {
        if (index > _state.get().getNewspaperList().size() || index < 0) {
            _state.setValue(new AddArticleState(ScreenConstants.THE_NEWSPAPER_SELECTED_IS_NOT_VALID, null, null, null, null));
        } else {
            Either<String, List<Article>> articlesByPaper = articleServicesImpl.getArticlesByNewspaper(_state.get().getNewspaperList().get(index - 1));
            AddArticleState jn;
            if (articlesByPaper.isRight()) {
                jn = (new AddArticleState(null, articlesByPaper.get(), _state.get().getNewspaperList(), _state.get().getTypeArtList(), null));
            } else {
                jn = new AddArticleState(articlesByPaper.getLeft(), null, null, null, null);
            }
            _state.setValue(jn);
        }
    }

    public void addArticle(String text, String desc, int indexType, int indexNews) {
        AddArticleState jn;
        if (text.equals("")) {
            jn = new AddArticleState(ScreenConstants.THE_ARTICLE_DOES_NOT_HAVE_A_NAME, _state.get().getArticlesList(), _state.get().getNewspaperList(), _state.getValue().getTypeArtList(), null);
        } else {
            Article article = new Article(text, desc, _state.get().getTypeArtList().get(indexType - 1).getId(),  _state.get().getNewspaperList().get(indexNews - 1).getId());
//            Either<String, List<Article>> articlesInTheNews = articleServicesImpl.getArticlesByNewspaper(_state.get().getNewspaperList().get(indexNews - 1));
//            if (articlesInTheNews.isRight()) {
//                if (articlesInTheNews.get().contains(article)) {
//                    String errorMsg = ScreenConstants.NEWSPAPER + ScreenConstants.SPACE + _state.get().getNewspaperList().get(indexNews - 1).getName_newspaper() + ScreenConstants.SPACE + ScreenConstants.ALREADY_HAS_AN_ARTICLE_NAMED +  ScreenConstants.QUOTATION + article.getName_article() + ScreenConstants.QUOTATION;
//                    jn = new AddArticleState(errorMsg, _state.get().getArticlesList(), _state.get().getNewspaperList(), _state.getValue().getTypeArtList(), null);
//                } else {
                    Either<String, String> addArticleSuccess = articleServicesImpl.addArticle(article);
                    if (addArticleSuccess.isRight()) {
                        List<Article> currentArticles = _state.get().getArticlesList();
                        currentArticles.add(article);
                        jn = new AddArticleState(null, articleServicesImpl.getArticlesByNewspaper(_state.get().getNewspaperList().get(indexNews-1)).get(), _state.get().getNewspaperList(), _state.getValue().getTypeArtList(), addArticleSuccess.get());
                    } else {
                        jn = new AddArticleState(addArticleSuccess.getLeft(), null, null, null, null);
                    }
                }
//            } else {
//                jn = new AddArticleState(articlesInTheNews.getLeft(), null, null, null, null);
//            }
//        }
        _state.setValue(jn);
    }
}
