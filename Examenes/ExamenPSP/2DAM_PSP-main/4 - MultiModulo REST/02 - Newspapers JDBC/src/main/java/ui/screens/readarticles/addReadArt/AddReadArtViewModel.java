package ui.screens.readarticles.addReadArt;

import model.*;
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
        _state = new SimpleObjectProperty<>(new AddReadArtState(null, null, null, null, null, null, null, null));
    }

    private final ObjectProperty<AddReadArtState> _state;

    public ReadOnlyObjectProperty<AddReadArtState> getState() {
        return _state;
    }


    public void loadScreen(Reader currentReader) {
        AddReadArtState addReadArtState;
        Either<String, List<Newspaper>> newsList = newspapersServicesImpl.getAll(currentReader);
        if (newsList.isRight()) {
            Either<String, List<QueryBadRatedArticles>> ratedWellArticles = readArtServices.getAllDifficultSpringQuery(currentReader.getId(), newsList.get().get(0).getId());
            if (ratedWellArticles.isRight()) {
                Either<String, List<Article>> articlesByNewspaper = articleServicesImpl.getArticlesByNewspaper(newsList.get().get(0));
                if (articlesByNewspaper.isRight() && !articlesByNewspaper.get().isEmpty()) {
                    addReadArtState = new AddReadArtState(null, currentReader, newsList.get(), newsList.get().get(0), articlesByNewspaper.get(), ratedWellArticles.get(), null, null);
                } else {
                    if (articlesByNewspaper.isRight()){
                        articlesByNewspaper = Either.left(ScreenConstants.THIS_NEWSPAPER_HAS_NO_ARTICLES);
                    }
                    addReadArtState = new AddReadArtState(articlesByNewspaper.getLeft(), currentReader, newsList.get(), newsList.get().get(0), null, ratedWellArticles.get(), null, null);
                }
            } else {
                addReadArtState = new AddReadArtState(ratedWellArticles.getLeft(), currentReader, newsList.get(), newsList.get().get(0), null, null, null, null);
            }
        } else {
            addReadArtState = new AddReadArtState(newsList.getLeft(), currentReader, null, null, null, null, null, null);
        }
        _state.setValue(addReadArtState);
    }

    public void loadArticlesByNews(Newspaper newsSelected) {
        AddReadArtState ar;
        Either<String, List<Article>> articlesByNewspaper = articleServicesImpl.getArticlesByNewspaper(newsSelected);
        if (articlesByNewspaper.isRight()) {
            Either<String, List<QueryBadRatedArticles>> ratedWellArticles = readArtServices.getAllDifficultSpringQuery(_state.getValue().getReaderSelected().getId(), newsSelected.getId());
            if (ratedWellArticles.isRight()) {
                ar = new AddReadArtState(null, _state.getValue().getReaderSelected(), _state.getValue().getNewspapers(), newsSelected, articlesByNewspaper.get(), ratedWellArticles.get(), null, null);
            } else {
                ar = new AddReadArtState(ratedWellArticles.getLeft(), _state.getValue().getReaderSelected(), _state.getValue().getNewspapers(), newsSelected, articlesByNewspaper.get(), null, null, null);
            }
        } else {
            ar = new AddReadArtState(articlesByNewspaper.getLeft(), _state.getValue().getReaderSelected(), _state.getValue().getNewspapers(), newsSelected, null, null, null, null);
        }
        _state.setValue(ar);
    }

    public void addReadArticle(Article articleSelected, Reader currentReader, Integer rating) {
        AddReadArtState ar;
        ReadArticle readArticle = new ReadArticle(currentReader.getId(), articleSelected.getId(), rating);
        Either<String, String> addReadArticle = readArtServices.add(readArticle);
        if (addReadArticle.isRight()) {
                ar = new AddReadArtState(null, currentReader, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), _state.getValue().getArticlesAvailable(),_state.getValue().getRatedWellArticles(), null, addReadArticle.get() );
        } else {
            ar = new AddReadArtState(addReadArticle.getLeft(), currentReader, _state.getValue().getNewspapers(), _state.getValue().getNewspaperSelected(), _state.getValue().getArticlesAvailable(), _state.getValue().getRatedWellArticles(), null, null);
        }
        _state.setValue(ar);
    }

    public void getRating(Article articleSelected) {
        AddReadArtState currentState = _state.getValue();
        Either<Integer, Integer> rating = readArtServices.getRating(articleSelected.getId(), currentState.getReaderSelected().getId());
        AddReadArtState ar = new AddReadArtState(null, currentState.getReaderSelected(), currentState.getNewspapers(), currentState.getNewspaperSelected(), currentState.getArticlesAvailable(), currentState.getRatedWellArticles(), null, null);
        if (rating.isRight()) {
            ar.setCurrentRating(rating.get());
            ar.setSuccess(ScreenConstants.WARNING_YOU_HAVE_ALREADY_RATED_THIS_ARTICLE_WITH_A + rating.get() + ScreenConstants.IF_YOU_CHANGE_IT_THE_PREVIOUS_RATING_WILL_BE_UPDATED);
        }
        else {
            ar.setCurrentRating(rating.getLeft());
        }
        _state.setValue(ar);

    }


}

