package servicios;

import data.DaoNewspaperSQL;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Newspaper;

import java.util.List;

public class ServicesNewspaperSQL {
    private final DaoNewspaperSQL daoNewspaperSQL;

    @Inject
    public ServicesNewspaperSQL(DaoNewspaperSQL daoNewspaperSQL) {
        this.daoNewspaperSQL = daoNewspaperSQL;
    }

    public Single<Either<String, List<Newspaper>>> getNewspapers() {
        return daoNewspaperSQL.getAll();
    }

    public Single<Either<String, Newspaper>> addNewspaper(Newspaper newspaper) {
        return daoNewspaperSQL.add(newspaper);
    }

    public Single<Either<String, Newspaper>> updateNewspaper(Newspaper newspaper) {
        return daoNewspaperSQL.update(newspaper);
    }

    public Single<Either<String, Boolean>> deleteNewspaper(String id) {
        return daoNewspaperSQL.delete(id);
    }
}