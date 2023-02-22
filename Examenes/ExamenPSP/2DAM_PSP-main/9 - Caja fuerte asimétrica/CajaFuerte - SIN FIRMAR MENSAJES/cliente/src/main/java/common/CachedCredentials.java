package common;

import jakarta.inject.Singleton;
import lombok.Data;
import org.utils.domain.modelo.dto.UserDTO;

import java.security.PrivateKey;
import java.security.PublicKey;


@Data
@Singleton
public class CachedCredentials {


    private UserDTO userLogged;
    private String jwt;

    //Si es una carpeta que no es mía, guardo el id de la carpeta padre para que los mensajes se envíen a la carpeta padre
    private int parentFolderId;
    private String folderPass;

    private PublicKey publicKeyUserLogged;
    private PrivateKey privateKeyUserLogged;

    public void invalidate(){
        jwt = null;
        folderPass = null;
        userLogged = null;
        publicKeyUserLogged = null;
        privateKeyUserLogged = null;
    }

}
