package ui.screens.newspapers.delNews;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Article;
import model.Newspaper;
import serivces.impl.ArticleServicesImpl;
import serivces.impl.NewspapersServicesImpl;
import ui.screens.common.ScreenConstants;

import java.util.List;

public class DelNewsViewModel {

    private NewspapersServicesImpl newspapersServicesImpl;
    private ArticleServicesImpl articleServicesImpl;


    @Inject
    public DelNewsViewModel(NewspapersServicesImpl newspapersServicesImpl, ArticleServicesImpl articleServicesImpl) {
        this.newspapersServicesImpl = newspapersServicesImpl;
        this.articleServicesImpl = articleServicesImpl;
        _state = new SimpleObjectProperty<>(new DelNewsState(null, null, null, null, null, null));
    }

    private final ObjectProperty<DelNewsState> _state;

    public ReadOnlyObjectProperty<DelNewsState> getState() {
        return _state;
    }

    public void loadScreen() {
        DelNewsState jn;
        Either<String, List<Newspaper>> newspaperList = newspapersServicesImpl.getAll();
        if (newspaperList.isRight()) {
            jn = new DelNewsState(null, null, newspaperList.get(), null, null, null);
        } else {
            jn = new DelNewsState(newspaperList.getLeft(), null, null, null, null, null);
        }
        _state.setValue(jn);
    }

    public void loadArticles(Newspaper newspaper) {
        DelNewsState jn;
        Either<String, List<Article>> articlesList = articleServicesImpl.getArticlesByNewspaper(newspaper);
        if (articlesList.isRight()) {
            jn = new DelNewsState(null, articlesList.get(), _state.getValue().getNewspaperList(), newspaper, null, null);
        } else {
            jn = new DelNewsState(articlesList.getLeft(), null, _state.getValue().getNewspaperList(), newspaper, null, null);
        }
        _state.setValue(jn);
    }

    public void deleteNewspaper(boolean confirmed) {
        DelNewsState jn;
        Either<String, String> newspaperDeleteSuccess = newspapersServicesImpl.delete(_state.getValue().getNewspaperSelected(), confirmed);
        if (newspaperDeleteSuccess.isRight()) {
            List<Newspaper> currentNews = _state.getValue().getNewspaperList();
            currentNews.remove(_state.getValue().getNewspaperSelected());
            jn = new DelNewsState(null, null, currentNews, null, null, newspaperDeleteSuccess.get());
        } else {
            String error = newspaperDeleteSuccess.getLeft();
            jn = new DelNewsState(null, _state.getValue().getArticlesList(), _state.getValue().getNewspaperList(), _state.getValue().getNewspaperSelected(), null, null);
            if (error.equals(ScreenConstants.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_NEWSPAPER_WITH_ALL_ITS_ARTICLES)){
                jn.setWarningMessage(error);
            } else {
                jn.setError(error);
            }
        }
        _state.setValue(jn);
    }
}
