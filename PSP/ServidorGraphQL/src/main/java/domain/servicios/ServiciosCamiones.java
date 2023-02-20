package domain.servicios;

import data.CamionesRepository;
import domain.excepciones.DatabaseException;
import domain.excepciones.ObjectNotFoundException;
import domain.modelo.Camion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosCamiones {

    private final CamionesRepository repository;

    public ServiciosCamiones(CamionesRepository repository) {
        this.repository = repository;
    }

    public List<Camion> getAll() {
        return repository.findAll();
    }

    public Camion getCamionById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No se encontró el camión"));
    }

    public void save(Camion camion) {
        try {
            repository.save(camion);
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar el camión");
        }
    }

    public void update(Camion camion) {
        try {
            repository.save(camion);
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar el camión");
        }
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar el camión");
        }
    }
}
