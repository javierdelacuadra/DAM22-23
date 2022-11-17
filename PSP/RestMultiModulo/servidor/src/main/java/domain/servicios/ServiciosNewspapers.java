package domain.servicios;

import dao.DaoNewspapers;
import dao.modelo.Newspaper;
import jakarta.inject.Inject;

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

    public boolean addNewspaper(Newspaper newspaper) {
        return dao.save(newspaper);
    }

    public boolean updateNewspaper(Newspaper newspaper) {
        return dao.update(newspaper);
    }

    public boolean deleteNewspaper(String id) {
        return dao.delete(id);
    }
}
