package servicios;

import data.DaoNewspaper;
import jakarta.inject.Inject;
import modelo.Newspaper;

import java.util.List;

public class ServicesNewspaper {

    private final DaoNewspaper daoNewspaper;

    @Inject
    public ServicesNewspaper(DaoNewspaper daoNewspaper) {
        this.daoNewspaper = daoNewspaper;
    }

    public List<Newspaper> getNewspapers() {
       return daoNewspaper.getNewspapers();
    }

    public boolean addNewspaper(Newspaper newspaper) {
        return daoNewspaper.saveNewspaper(newspaper);
    }

    public boolean deleteNewspaper(Newspaper newspaper) {
        if (!daoNewspaper.checkArticles(newspaper)) {
            return daoNewspaper.deleteNewspaper(newspaper);
        } else {
            return false;
        }
    }
}
