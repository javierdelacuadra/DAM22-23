package spring.controllers;

import domain.modelo.Camion;
import domain.modelo.Conductor;
import domain.servicios.ServiciosConductores;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConductoresController {

    private final ServiciosConductores servicios;

    public ConductoresController(ServiciosConductores servicios) {
        this.servicios = servicios;
    }

    @QueryMapping
    public List<Conductor> getAllConductores() {
        return servicios.getAll();
    }

    @QueryMapping
    public Conductor conductor(@Argument Integer id) {
        return servicios.getConductorById(id);
    }

    @MutationMapping
    public Conductor createConductor(@Argument String nombre, @Argument String telefono) {
        Conductor conductor = new Conductor(nombre, telefono);
        servicios.save(conductor);
        return conductor;
    }

    @MutationMapping
    public Conductor updateConductor(@Argument Integer id, @Argument String nombre, @Argument String telefono, @Argument Camion camion) {
        Conductor conductor = new Conductor(id, nombre, telefono, camion);
        servicios.update(conductor);
        return conductor;
    }

    @MutationMapping
    public Integer deleteConductor(@Argument Integer id) {
        servicios.delete(id);
        return id;
    }
}
