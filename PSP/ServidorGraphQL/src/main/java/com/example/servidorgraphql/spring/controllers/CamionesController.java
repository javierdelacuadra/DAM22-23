package com.example.servidorgraphql.spring.controllers;

import com.example.servidorgraphql.domain.modelo.Camion;
import com.example.servidorgraphql.domain.modelo.Conductor;
import com.example.servidorgraphql.domain.servicios.ServiciosCamiones;
import com.example.servidorgraphql.domain.servicios.ServiciosConductores;
import com.example.servidorgraphql.domain.servicios.ServiciosEmpresas;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CamionesController {

    private final ServiciosCamiones servicios;
    private final ServiciosConductores serviciosConductores;

    public CamionesController(ServiciosCamiones camionService, ServiciosConductores serviciosConductores) {
        this.servicios = camionService;
        this.serviciosConductores = serviciosConductores;
    }

    @QueryMapping
    public List<Camion> getAllCamiones() {
        return servicios.getAll();
    }

    @QueryMapping
    public Camion getCamion(@Argument Integer id) {
        return servicios.getCamionById(id);
    }

    @SchemaMapping
    public Conductor conductor(Camion camion) {
        return serviciosConductores.getConductorByCamion(camion);
    }

    @MutationMapping
    public Camion createCamion(@Argument String modelo, @Argument String fechaConstruccion, @Argument Integer empresaId) {
        Camion camion = new Camion();
        camion.setModelo(modelo);
        camion.setFechaConstruccion(fechaConstruccion);
        camion.setEmpresaID(empresaId);
        servicios.save(camion);

        return camion;
    }

    @MutationMapping
    public Camion updateCamion(@Argument Integer id, @Argument String modelo, @Argument String fechaConstruccion, @Argument Integer empresaId) {
        Camion camion = new Camion(id, modelo, fechaConstruccion, empresaId);
        servicios.update(camion);
        return camion;
    }

    @MutationMapping
    public Integer deleteCamion(@Argument Integer id) {
        servicios.delete(id);
        return id;
    }
}