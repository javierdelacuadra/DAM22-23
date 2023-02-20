package spring.controllers;

import domain.modelo.Camion;
import domain.servicios.ServiciosCamiones;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CamionesController {

    private final ServiciosCamiones servicios;

    public CamionesController(ServiciosCamiones camionService) {
        this.servicios = camionService;
    }

    @QueryMapping
    public List<Camion> getAllCamiones() {
        return servicios.getAll();
    }

    @QueryMapping
    public Camion camion(@Argument Integer id) {
        return servicios.getCamionById(id);
    }

    @MutationMapping
    public Camion createCamion(@Argument String modelo, @Argument String fechaConstruccion) {
        Camion camion = new Camion();
        camion.setModelo(modelo);
        camion.setFechaConstruccion(fechaConstruccion);
        servicios.save(camion);

        return camion;
    }

    @MutationMapping
    public Camion updateCamion(@Argument Integer id, @Argument String modelo, @Argument String fechaConstruccion) {
        Camion camion = new Camion(id, modelo, fechaConstruccion);
        servicios.update(camion);
        return camion;
    }

    @MutationMapping
    public Integer deleteCamion(@Argument Integer id) {
        servicios.delete(id);
        return id;
    }
}