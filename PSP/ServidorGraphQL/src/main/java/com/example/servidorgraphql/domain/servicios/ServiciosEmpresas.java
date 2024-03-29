package com.example.servidorgraphql.domain.servicios;

import com.example.servidorgraphql.data.EmpresasRepository;
import com.example.servidorgraphql.domain.excepciones.DatabaseException;
import com.example.servidorgraphql.domain.excepciones.ObjectNotFoundException;
import com.example.servidorgraphql.domain.modelo.Empresa;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosEmpresas {

    private final EmpresasRepository repository;

    public ServiciosEmpresas(EmpresasRepository repository) {
        this.repository = repository;
    }

    public List<Empresa> getAll() {
        return repository.findAll();
    }

    public Empresa getEmpresaById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No se encontró la empresa"));
    }

    public void save(Empresa empresa) {
        try {
            repository.save(empresa);
        } catch (Exception e) {
            throw new DatabaseException("Error al guardar la empresa");
        }
    }

    public void update(Empresa empresa) {
        try {
            repository.save(empresa);
        } catch (Exception e) {
            throw new DatabaseException("Error al actualizar la empresa");
        }
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new DatabaseException("Error al eliminar la empresa");
        }
    }
}