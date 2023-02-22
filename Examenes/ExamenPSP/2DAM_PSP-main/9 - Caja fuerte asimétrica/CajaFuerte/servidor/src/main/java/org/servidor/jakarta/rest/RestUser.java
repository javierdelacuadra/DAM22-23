package org.servidor.jakarta.rest;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.servidor.domain.servicios.impl.ServiciosUserImpl;
import org.servidor.jakarta.common.RestConstants;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.ApiError;
import org.utils.domain.modelo.User;
import org.utils.domain.modelo.dto.UserDTO;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@Path(AplicationConstants.PATH_USERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUser {

    private final ServiciosUserImpl userServices;

    @Context
    SecurityContext securityContext;

    @Named(RestConstants.KEY_PROVIDER)
    @Inject
    Key key; // Esta clave viene inyectada desde el KeyProvider
    @Inject
    public RestUser(ServiciosUserImpl userServices) {
        this.userServices = userServices;
    }

    @POST
    public Response createReader(User user) {
        return Response.status(Response.Status.CREATED).entity(userServices.add(user)).build();
    }

    @GET
    @Path(AplicationConstants.PATH_PARAM_USER_NAME + AplicationConstants.PATH_PARAM_CERT)
    @RolesAllowed({RestConstants.ROLE_ADMIN, RestConstants.ROLE_USER})
    public String getCertificate(@PathParam(AplicationConstants.USER_NAME) String username) {
        return userServices.getCertificado(username);
    }



    @POST
    @Path(AplicationConstants.PATH_LOGIN)
    public Response login(UserDTO userDTO) {
        if (securityContext.getUserPrincipal() != null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        User user = userServices.login(userDTO.getName(), userDTO.getFirma());
        if (user != null){
            String jws = generateToken(new CredentialValidationResult(user.getName(), Set.of(user.getRole().toString())));
            return Response.status(Response.Status.OK).header(AplicationConstants.JWT, jws).entity(user).build();
        }
        else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ApiError(RestConstants.USUARIO_O_CONTRASENA_INCORRECTOS, LocalDateTime.now())).build();
        }
    }

    private String generateToken(CredentialValidationResult c) {
        return Jwts.builder()
                .setSubject(AplicationConstants.JWT)
                .setExpiration(Date
                        .from(LocalDateTime.now().plusSeconds(30).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim(RestConstants.USER, c.getCallerPrincipal().getName())
                .claim(RestConstants.ROLES, c.getCallerGroups())
                .signWith(key).compact();

    }

}
