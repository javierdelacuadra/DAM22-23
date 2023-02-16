package spring.controllers;

import domain.modelo.Empresa;
import domain.servicios.ServiciosEmpresas;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EmpresasController {

    private final ServiciosEmpresas servicios;

    public EmpresasController(ServiciosEmpresas servicios) {
        this.servicios = servicios;
    }

    @QueryMapping
    public List<Empresa> getAllEmpresas() {
        return servicios.getAll();
    }

    @QueryMapping
    public Empresa empresa(@Argument Integer id) {
        return servicios.getEmpresaById(id);
    }

    @MutationMapping
    public Empresa createEmpresa(@Argument String nombre, @Argument String fechaCreacion) {
        Empresa empresa = new Empresa(nombre, fechaCreacion);
        servicios.save(empresa);
        return empresa;
    }

    @MutationMapping
    public Empresa updateEmpresa(@Argument Integer id, @Argument String nombre, @Argument String direccion) {
        Empresa empresa = new Empresa(id, nombre, direccion);
        servicios.update(empresa);
        return empresa;
    }

    @MutationMapping
    public Integer deleteEmpresa(@Argument Integer id) {
        servicios.delete(id);
        return id;
    }

}