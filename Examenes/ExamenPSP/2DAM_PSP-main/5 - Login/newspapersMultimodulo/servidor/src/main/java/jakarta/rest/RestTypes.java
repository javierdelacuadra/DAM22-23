package jakarta.rest;

import domain.servicios.impl.TypeServicesImpl;
import jakarta.inject.Inject;
import jakarta.common.RestConstants;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.miutils.domain.modelo.TypeArt;

import java.util.List;

@Path(RestConstants.PATH_TYPES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestTypes {

    TypeServicesImpl typeServices;

    @Inject
    public RestTypes(TypeServicesImpl typeServices) {
        this.typeServices = typeServices;
    }


    @GET
    public List<TypeArt> getTypes() {
        return typeServices.getAllTypes();
    }
}
