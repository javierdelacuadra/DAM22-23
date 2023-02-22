package com.serverql.graphqlserver.spring.graphql;

import com.serverql.graphqlserver.domain.modelo.Mascota;
import com.serverql.graphqlserver.domain.modelo.Persona;
import com.serverql.graphqlserver.domain.servicios.ServiciosMascota;
import com.serverql.graphqlserver.domain.servicios.ServiciosPersona;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MascotasController {

    private final ServiciosMascota serviciosMascota;

    private final ServiciosPersona serviciosPersona;

    public MascotasController(ServiciosMascota serviciosMascota, ServiciosPersona serviciosPersona) {
        this.serviciosMascota = serviciosMascota;
        this.serviciosPersona = serviciosPersona;
    }

    @QueryMapping
    public List<Mascota> allMascotasUserLogged() {
        return serviciosMascota.getAllByUserLogged(serviciosPersona.getPersonaByUsername());
    }

    @MutationMapping
    public Mascota createMascota(@Argument String name, @Argument String type, @Argument int age, @Argument int persona) {
//        mutation {
//            createMascota(
//                    name:"masqui"
//            type:"canario"
//            age:5
//            persona:11
//){
//                id
//                        name
//                persona{
//                    name
//                            surname
//                }
//            }
//
//        }
        Mascota m = new Mascota();
        m.setId(0);
        m.setName(name);
        m.setType(type);
        m.setAge(age);
        m.setPersona(persona);
        serviciosMascota.createMascota(m);
        return m;
    }

    @MutationMapping
    public Mascota updateMascota(@Argument int id, @Argument String name, @Argument String type, @Argument int age) {
        Mascota m = new Mascota();
        m.setId(id);
        m.setName(name);
        m.setType(type);
        m.setAge(age);
        return serviciosMascota.updateMascota(m);
    }

    @MutationMapping
    public Mascota deleteMascota(@Argument int id) {
        return serviciosMascota.deleteMascota(id);
    }

    @SchemaMapping(typeName = "Persona", field = "mascotas")
    public List<Mascota> mascotas(Persona persona) {
        return serviciosMascota.getMascotasByPersonaId(persona.getId());
    }
}

