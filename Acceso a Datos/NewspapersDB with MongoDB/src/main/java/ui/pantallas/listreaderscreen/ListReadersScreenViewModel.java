package ui.pantallas.listreaderscreen;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArticleType;
import model.Newspaper;
import model.Reader;
import servicios.ServicesNewspaperSQL;
import servicios.ServicesReadArticles;
import servicios.ServicesReadersSQL;
import servicios.ServicesTypes;

public class ListReadersScreenViewModel {

    private final ServicesNewspaperSQL servicesNewspaperSQL;
    private final ServicesReadersSQL servicesReadersSQL;
    private final ServicesReadArticles servicesReadArticles;
    private final ServicesTypes servicesTypes;

    @Inject
    public ListReadersScreenViewModel(ServicesNewspaperSQL servicesNewspaperSQL, ServicesReadersSQL servicesReadersSQL, ServicesReadArticles servicesReadArticles, ServicesTypes servicesTypes) {
        this.servicesNewspaperSQL = servicesNewspaperSQL;
        this.servicesReadersSQL = servicesReadersSQL;
        this.servicesReadArticles = servicesReadArticles;
        this.servicesTypes = servicesTypes;
    }

    public ObservableList<Reader> getReaders() {
        return FXCollections.observableArrayList(servicesReadersSQL.getAllReaders().get());
    }

    public ObservableList<Newspaper> getNewspapers() {
        return FXCollections.observableArrayList(servicesNewspaperSQL.getNewspapers().get());
    }

//    public Either<Integer, ObservableList<Reader>> getReadersByNewspaper(Newspaper newspaper) {
//        return servicesReadersSQL.getReadersByNewspaper(newspaper).map(FXCollections::observableArrayList);
//    }

    public Either<Integer, ObservableList<Reader>> getReadersByArticleType(ArticleType type) {
        return servicesReadersSQL.getReadersByArticleType(type).map(FXCollections::observableArrayList);
    }

    public ObservableList<ArticleType> getArticleTypes() {
        return FXCollections.observableArrayList(servicesTypes.getArticleTypes().get());
    }

//    public Map<Double, String> getAvgRating(Integer idReader) {
//        return servicesReadArticles.getAvgRating(idReader);
//    }
}
