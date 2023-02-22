package com.example.servidorgraphql.domain.servicios;

import com.example.servidorgraphql.data.CamionesRepository;
import com.example.servidorgraphql.domain.excepciones.DatabaseException;
import com.example.servidorgraphql.domain.excepciones.ObjectNotFoundException;
import com.example.servidorgraphql.domain.modelo.Camion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiciosCamiones {

    private final CamionesRepository repository;

    public ServiciosCamiones(CamionesRepository repository) {
        this.repository = repository;
    }

    public List<Camion> getAll() {
        List<Camion> camiones = new ArrayList<>(repository.findAll());
        return camiones.isEmpty() ? new ArrayList<>() : camiones;
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

    public List<Camion> getCamionesByEmpresaId(int id) {
        return repository.findByEmpresaId(id);
    }
}
