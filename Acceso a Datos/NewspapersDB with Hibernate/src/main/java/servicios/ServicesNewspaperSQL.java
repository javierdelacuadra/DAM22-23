package servicios;

import data.DaoNewspaper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;

import java.util.List;

public class ServicesNewspaperSQL {
    private final DaoNewspaper daoNewspaper;

    @Inject
    public ServicesNewspaperSQL(DaoNewspaper daoNewspaper) {
        this.daoNewspaper = daoNewspaper;
    }

    public Either<Integer, List<Newspaper>> getNewspapers() {
        return daoNewspaper.getAll();
    }

    public Integer addNewspaper(Newspaper newspaper) {
        return daoNewspaper.add(newspaper);
    }

    public Integer deleteNewspaper(Integer id) {
        return daoNewspaper.delete(id);
    }

    public Integer updateNewspaper(Newspaper newspaper) {
        return daoNewspaper.update(newspaper);
    }
}