package servicios;

import data.DaoNewspaper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;

import java.util.List;
import java.util.Map;

public class ServicesNewspaperSQL {
    private final DaoNewspaper daoNewspaper;

    @Inject
    public ServicesNewspaperSQL(DaoNewspaper daoNewspaper) {
        this.daoNewspaper = daoNewspaper;
    }

    public Either<Integer, List<Newspaper>> getNewspapers() {
        return daoNewspaper.getAll();
    }

    public Newspaper getArticlesByNameNewspaper(Newspaper newspaper) {
        return daoNewspaper.get(newspaper);
    }

    public Integer addNewspaper(Newspaper newspaper) {
        return daoNewspaper.add(newspaper);
    }

    public Integer deleteNewspaper(Newspaper newspaper) {
        return daoNewspaper.delete(newspaper);
    }

    public Integer updateNewspaper(Newspaper newspaper) {
        return daoNewspaper.update(newspaper);
    }

    public Map<String, Integer> getNbrArticles(int idNewspaper) {
        return daoNewspaper.getNbrArticles(idNewspaper);
    }
}