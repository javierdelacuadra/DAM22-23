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

    public Either<Integer, List<Newspaper>> addNewspaper(Newspaper newspaper) {
        return daoNewspaperSQL.addNewspaper(newspaper);
    }

    public Either<Integer, List<Newspaper>> deleteNewspaper(Integer id) {
        return daoNewspaperSQL.deleteNewspaper(id);
    }

    public Either<Integer, List<Newspaper>> updateNewspaper(Newspaper newspaper) {
        return daoNewspaperSQL.updateNewspaper(newspaper);
    }
}