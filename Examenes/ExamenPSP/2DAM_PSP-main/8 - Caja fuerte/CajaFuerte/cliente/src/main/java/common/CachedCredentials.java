package common;

import jakarta.inject.Singleton;
import lombok.Data;


@Data
@Singleton
public class CachedCredentials {

    private String user;
    private String pass;
    private String jwt;
    private String folderPass;

    public void invalidate(){
        user = null;
        pass = null;
        jwt = null;
        folderPass = null;
    }
}
