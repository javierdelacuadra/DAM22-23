package domain.servicios;

import data.ConductoresRepository;
import domain.excepciones.DatabaseException;
import domain.excepciones.ObjectNotFoundException;
import domain.modelo.Conductor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosConductores {

    private final ConductoresRepository repository;

    public ServiciosConductores(ConductoresRepository repository) {
        this.repository = repository;
    }

    public List<Conductor> getAll() {
        return repository.findAll();
    }

    public Conductor getConductorById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No se encontró el conductor"));
    }

    public void save(Conductor conductor) {
        try {
            repository.save(conductor);
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar el conductor");
        }
    }

    public void update(Conductor conductor) {
        try {
            repository.save(conductor);
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar el conductor");
        }
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar el conductor");
        }
    }
}
