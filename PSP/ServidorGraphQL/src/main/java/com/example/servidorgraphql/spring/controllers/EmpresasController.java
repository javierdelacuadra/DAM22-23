package com.example.servidorgraphql.spring.controllers;

import com.example.servidorgraphql.domain.modelo.Camion;
import com.example.servidorgraphql.domain.modelo.Empresa;
import com.example.servidorgraphql.domain.servicios.ServiciosCamiones;
import com.example.servidorgraphql.domain.servicios.ServiciosEmpresas;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EmpresasController {

    private final ServiciosEmpresas servicios;
    private final ServiciosCamiones serviciosCamiones;

    public EmpresasController(ServiciosEmpresas servicios, ServiciosCamiones serviciosCamiones) {
        this.servicios = servicios;
        this.serviciosCamiones = serviciosCamiones;
    }

    @QueryMapping
    public List<Empresa> getAllEmpresas() {
        return servicios.getAll();
    }

    @QueryMapping
    public Empresa empresa(@Argument Integer id) {
        return servicios.getEmpresaById(id);
    }

    @SchemaMapping
    public List<Camion> camiones(Empresa empresa) {
        return serviciosCamiones.getCamionesByEmpresaId(empresa.getId());
    }

    @MutationMapping
    public Empresa createEmpresa(@Argument String nombre, @Argument String direccion) {
        Empresa empresa = new Empresa(nombre, direccion);
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