package servicios;

import data.DaoTypes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.ArticleType;

import java.util.List;

public class ServicesTypes {

    private final DaoTypes daoTypes;

    @Inject
    public ServicesTypes(DaoTypes daoTypes) {
        this.daoTypes = daoTypes;
    }

    public Either<Integer, List<ArticleType>> getArticleTypes() {
        return daoTypes.getAll();
    }

    public Either<Integer, ArticleType> getMostReadType() {
        return daoTypes.get();
    }
}
