package ui.screens.articles.listArticles;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Article;
import model.QueryArticlesAndNews;
import model.QueryDescAndReaders;
import model.TypeArt;
import serivces.impl.ArticleServicesImpl;
import serivces.impl.TypeServicesImpl;
import ui.screens.common.ScreenConstants;

import java.util.List;

public class ListArticlesViewModel {

    ArticleServicesImpl articleServicesImpl;
    TypeServicesImpl typeServicesImpl;

    @Inject
    public ListArticlesViewModel(ArticleServicesImpl articleServicesImpl, TypeServicesImpl typeServicesImpl) {
        this.articleServicesImpl = articleServicesImpl;
        this.typeServicesImpl = typeServicesImpl;
        _state = new SimpleObjectProperty<>(new ListArticlesState(null, null, null, null, null, null));
    }

    private final ObjectProperty<ListArticlesState> _state;

    public ReadOnlyObjectProperty<ListArticlesState> getState() {
        return _state;
    }

    public void loadArticlesAndTypes() {
        ListArticlesState es;
        Either<String, List<Article>> list = articleServicesImpl.getAllArticles();
        if (list.isRight()) {
            Either<String, QueryDescAndReaders> queryDescAndReaders = articleServicesImpl.getQuery(list.get().get(0).getId());
            if (queryDescAndReaders.isRight()) {
                Either<String, List<TypeArt>> listTypeArts = typeServicesImpl.getAllTypes();
                if (listTypeArts.isRight()) {
                    Either<String, List<Article>> listArticlesFiltered = articleServicesImpl.getArticlesByType(listTypeArts.get().get(0));
                    if (listArticlesFiltered.isRight()) {
                        Either<String, List<QueryArticlesAndNews>> listQueryArticlesAndNews = articleServicesImpl.getAllQuerySpring(listTypeArts.get().get(0).getId());
                        if (listQueryArticlesAndNews.isRight()) {
                            es = new ListArticlesState(null, list.get(), queryDescAndReaders.get(), listTypeArts.get(), listArticlesFiltered.get(), listQueryArticlesAndNews.get());
                        } else {
                            es = new ListArticlesState(listQueryArticlesAndNews.getLeft(), list.get(), queryDescAndReaders.get(), listTypeArts.get(), listArticlesFiltered.get(), null);
                        }
                    } else {
                        es = new ListArticlesState(listArticlesFiltered.getLeft(), list.get(), queryDescAndReaders.get(), null, null, null);
                    }

                } else {
                    es = new ListArticlesState(listTypeArts.getLeft(), list.get(), queryDescAndReaders.get(), null, null, null);
                }
            } else {
                es = new ListArticlesState(queryDescAndReaders.getLeft(), list.get(), null, null, null, null);
            }
        } else {
            es = new ListArticlesState(list.getLeft(), null, null, null, null, null);
        }
        _state.setValue(es);
    }


    public void loadArticlesByType(TypeArt typeArt) {
        ListArticlesState es;
        Either<String, List<Article>> list = articleServicesImpl.getArticlesByType(typeArt);
        if (list.isRight()) {
            if (!list.isEmpty()) {
                Either<String, List<QueryArticlesAndNews>> listQueryArticlesAndNews = articleServicesImpl.getAllQuerySpring(typeArt.getId());
                if (listQueryArticlesAndNews.isRight()) {
                    es = new ListArticlesState(null, _state.get().getArticles(), _state.getValue().getQueryDescAndReaders(), _state.get().getTypeArts(), list.get(), listQueryArticlesAndNews.get());
                } else {
                    es = new ListArticlesState(listQueryArticlesAndNews.getLeft(), null, null, null, list.get(), null);
                }
            } else {
                es = new ListArticlesState(ScreenConstants.THE_ARTICLES_TYPES_OR_NEWSPAPERS_WERE_NOT_LOADED_PROPERLY, _state.getValue().getArticles(), _state.getValue().getQueryDescAndReaders(), _state.getValue().getTypeArts(), null, null);
            }
        } else {
            es = new ListArticlesState(list.getLeft(), _state.getValue().getArticles(), _state.getValue().getQueryDescAndReaders(), _state.getValue().getTypeArts(), null, null);
        }
        _state.setValue(es);
    }

    public void updateQuery(Article newArtSelected) {
        ListArticlesState es;
        Either<String, QueryDescAndReaders> queryDescAndReaders = articleServicesImpl.getQuery(newArtSelected.getId());
        if (queryDescAndReaders.isRight()) {
            es = new ListArticlesState(null, _state.getValue().getArticles(), queryDescAndReaders.get(), _state.getValue().getTypeArts(), _state.getValue().getArticlesFiltered(), _state.getValue().getQueryArticlesAndNewsList());
        } else {
            es = new ListArticlesState(queryDescAndReaders.getLeft(), _state.getValue().getArticles(), null, _state.getValue().getTypeArts(), _state.getValue().getArticlesFiltered(), _state.getValue().getQueryArticlesAndNewsList());
        }
        _state.setValue(es);
    }
}
