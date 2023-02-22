package com.example.servidorgraphql.spring.controllers;

import com.example.servidorgraphql.domain.modelo.Camion;
import com.example.servidorgraphql.domain.modelo.Conductor;
import com.example.servidorgraphql.domain.servicios.ServiciosCamiones;
import com.example.servidorgraphql.domain.servicios.ServiciosConductores;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConductoresController {

    private final ServiciosConductores servicios;
    private final ServiciosCamiones serviciosCamiones;

    public ConductoresController(ServiciosConductores servicios, ServiciosCamiones serviciosCamiones) {
        this.servicios = servicios;
        this.serviciosCamiones = serviciosCamiones;
    }

    @QueryMapping
    public List<Conductor> getAllConductores() {
        return servicios.getAll();
    }

    @QueryMapping
    public Conductor conductor(@Argument Integer id) {
        return servicios.getConductorById(id);
    }

    @SchemaMapping
    public Camion camion(Conductor conductor) {
        return serviciosCamiones.getCamionById(conductor.getCamionID());
    }

    @MutationMapping
    public Conductor createConductor(@Argument String nombre, @Argument String telefono, @Argument Integer camionId) {
        Conductor conductor = new Conductor(nombre, telefono, camionId);
        servicios.save(conductor);
        return conductor;
    }

    @MutationMapping
    public Conductor updateConductor(@Argument Integer id, @Argument String nombre, @Argument String telefono, @Argument Integer camionId) {
        Conductor conductor = new Conductor(id, nombre, telefono, camionId);
        servicios.update(conductor);
        return conductor;
    }

    @MutationMapping
    public Integer deleteConductor(@Argument Integer id) {
        servicios.delete(id);
        return id;
    }
}
