package servicios;

import data.DaoNewspaper;
import data.DaoSubscriptions;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;

import java.util.List;

public class ServicesNewspaperSQL {
    private final DaoNewspaper daoNewspaper;
    private final DaoSubscriptions daoSubscriptions;

    @Inject
    public ServicesNewspaperSQL(DaoNewspaper daoNewspaper, DaoSubscriptions daoSubscriptions) {
        this.daoNewspaper = daoNewspaper;
        this.daoSubscriptions = daoSubscriptions;
    }

    public Either<Integer, List<Newspaper>> getNewspapers() {
        return daoNewspaper.get();
    }

    public Newspaper getArticlesByNameNewspaper(Newspaper newspaper) {
        return daoNewspaper.get(newspaper);
    }

    public Integer addNewspaper(Newspaper newspaper) {
        return daoNewspaper.add(newspaper);
    }

    public Integer deleteNewspaper(Newspaper newspaper) {
        if (newspaper.getArticles().isEmpty()) {
            if (daoSubscriptions.get(newspaper).get().isEmpty()) {
                return daoNewspaper.delete(newspaper);
            } else {
                return -3;
            }
        } else {
            return -2;
        }
    }

    public Integer updateNewspaper(Newspaper newspaper) {
        return daoNewspaper.update(newspaper);
    }
}