package servicios;

import data.DaoNewspaperSQL;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;

import java.util.List;

public class ServicesNewspaperSQL {
    private final DaoNewspaperSQL daoNewspaperSQL;

    @Inject
    public ServicesNewspaperSQL(DaoNewspaperSQL daoNewspaperSQL) {
        this.daoNewspaperSQL = daoNewspaperSQL;
    }

    public Either<Integer, List<Newspaper>> getNewspapers() {
        return daoNewspaperSQL.getAll();
    }
}