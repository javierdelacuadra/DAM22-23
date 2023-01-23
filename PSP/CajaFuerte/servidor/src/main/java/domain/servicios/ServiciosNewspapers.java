package domain.servicios;

import dao.DaoNewspapers;
import jakarta.inject.Inject;
import modelo.Newspaper;

import java.util.List;

public class ServiciosNewspapers {

    private final DaoNewspapers dao;

    @Inject
    public ServiciosNewspapers(DaoNewspapers dao) {
        this.dao = dao;
    }

    public List<Newspaper> getNewspapers() {
        return dao.getAll();
    }

    public Newspaper get(String id) {
        return dao.get(id);
    }

    public Newspaper addNewspaper(Newspaper newspaper) {
        return dao.save(newspaper);
    }

    public Newspaper updateNewspaper(Newspaper newspaper) {
        return dao.update(newspaper);
    }

    public boolean deleteNewspaper(String id) {
        return dao.delete(id);
    }
}
