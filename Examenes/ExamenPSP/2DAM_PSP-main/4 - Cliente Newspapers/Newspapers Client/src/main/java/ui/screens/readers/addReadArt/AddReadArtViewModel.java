package ui.screens.readers.addReadArt;

import model.Article;
import model.Newspaper;
import model.ReadArticle;
import model.Reader;
import serivces.impl.ArticleServicesImpl;
import serivces.impl.NewspapersServicesImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import serivces.impl.ReadArticleServicesImpl;
import ui.screens.common.ScreenConstants;

import java.util.List;

public class AddReadArtViewModel {

    private ReadArticleServicesImpl readArtServices;
    private NewspapersServicesImpl newspapersServicesImpl;
    private ArticleServicesImpl articleServicesImpl;


    @Inject
    public AddReadArtViewModel(ReadArticleServicesImpl readArtServices, NewspapersServicesImpl newspapersServicesImpl, ArticleServicesImpl articleServicesImpl) {
        this.readArtServices = readArtServices;
        this.newspapersServicesImpl = newspapersServicesImpl;
        this.articleServicesImpl = articleServicesImpl;
        _state = new SimpleObjectProperty<>(new AddReadArtState(null,  null, null, null, null, null));
    }

    private final ObjectProperty<AddReadArtState> _state;

    public ReadOnlyObjectProperty<AddReadArtState> getState() {
        return _state;
    }


    public void loadScreen(Reader currentReader) {
        AddReadArtState addReadArtState;
        Either<String, List<Newspaper>> newsList = newspapersServicesImpl.getAll(currentReader);
        if (newsList.isRight()){
            Either<String, List<Article>> unreadArticles = articleServicesImpl.getUnreadArtsByReaderAndNews(currentReader, newsList.get().get(0));
            if (unreadArticles.isRight()){
                addReadArtState = new AddReadArtState(null, currentReader, newsList.get(), newsList.get().get(0), unreadArticles.get(), null);
            } else {
                addReadArtState = new AddReadArtState(unreadArticles.getLeft(), currentReader, newsList.get(), newsList.get().get(0), null, null);
            }
        }else {
            addReadArtState = new AddReadArtState(newsList.getLeft(), currentReader, null, null, null, null);
        }
        _state.setValue(addReadArtState);
    }

    public void loadUnreadArticles(Newspaper newsSelected) {
        AddReadArtState ar;
        Either<String, List<Article>> unreadArts = articleServicesImpl.getUnreadArtsByReaderAndNews(_state.getValue().getReaderSelected(), newsSelected);
        if (unreadArts.isRight()){
            ar = new AddReadArtState(null, _state.getValue().getReaderSelected(), _state.getValue().getNewspapers(), newsSelected, unreadArts.get(), null);
        } else {
            ar = new AddReadArtState(unreadArts.getLeft(), _state.getValue().getReaderSelected(), _state.getValue().getNewspapers(), newsSelected, null, null);
        }
        _state.setValue(ar);
    }
    public void addReadArticle(Article articleSelected, Reader currentReader, Integer rating) {
        AddReadArtState ar;
        ReadArticle readArticle = new ReadArticle(currentReader.getId(), articleSelected.getId(), rating);
        Either<String, String> addReadArticle = readArtServices.add(readArticle);
        if (addReadArticle.isRight()) {
            List<Article> currentUnreadArticles = _state.getValue().getUnreadArticles();
            currentUnreadArticles.remove(articleSelected);
            if (!currentUnreadArticles.isEmpty()) {
                ar = new AddReadArtState(null, currentReader, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), currentUnreadArticles, currentReader.getName() + addReadArticle.get());
            } else {
                ar = new AddReadArtState(ScreenConstants.YOU_HAVE_READ_ALL_ARTICLES_IN + _state.getValue().getNewspaperSelected().getName_newspaper(), currentReader, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), null, addReadArticle.get());
            }
        } else {
            ar = new AddReadArtState(addReadArticle.getLeft(), currentReader, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), _state.getValue().getUnreadArticles(), null);
        }
        _state.setValue(ar);
    }
}

