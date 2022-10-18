package servicios;

import data.DaoNewspaper;
import jakarta.inject.Inject;
import model.Newspaper;

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

    public void deleteNewspaper(Newspaper newspaper) {
        daoNewspaper.getNewspapers().stream().filter(newspaper1 -> newspaper1.getId() == newspaper.getId())
                .findFirst().ifPresent(daoNewspaper::deleteNewspaper);
    }

    public boolean checkNewspaper(Newspaper newspaper) {
        return daoNewspaper.checkArticles(newspaper);
    }
}
