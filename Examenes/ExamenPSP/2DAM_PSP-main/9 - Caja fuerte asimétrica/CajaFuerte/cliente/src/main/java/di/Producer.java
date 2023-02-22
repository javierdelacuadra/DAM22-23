package di;

import common.CommonConstants;
import config.Configuration;
import dao.impl.CredentialsDaoImpl;
import io.vavr.control.Either;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;
import org.seguridad.EncriptacionAsimetrica;
import org.utils.common.AplicationConstants;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class Producer {

    @Produces
    @Singleton
    @Named(CommonConstants.SERVER_PK)
    public PublicKey getServerPublicKey(CredentialsDaoImpl credentialsDao, EncriptacionAsimetrica encriptacionAsimetrica, Configuration configuration) {
        Path fullPath = Paths.get(configuration.getServerPublicKey());
        try {
            Either<String, PublicKey> getPkFromFile = encriptacionAsimetrica.getPublicKeyFromStringBase64(Base64.getUrlEncoder().encodeToString(Files.readAllBytes(fullPath)));
            if (getPkFromFile.isLeft())
                throw new RuntimeException(getPkFromFile.getLeft());
            else
                return getPkFromFile.get();
        } catch (Exception noExisteFichero) {
            log.error(noExisteFichero.getMessage(), noExisteFichero);
            CompletableFuture<PublicKey> publicKeyFuture = new CompletableFuture<>();
            credentialsDao.getPublic()
                    .subscribe(either -> {
                        if (either.isLeft())
                            throw new RuntimeException(either.getLeft());
                        else {
                            // Se decodifica la clave pública del servidor
                            encriptacionAsimetrica.getPublicKeyFromStringBase64(either.get().headers().get(AplicationConstants.PUBLIC_KEY))
                                    .peek(pk -> {
                                        try {
                                            Files.createDirectories(fullPath.getParent());
                                            Files.createFile(fullPath);
                                            Files.write(fullPath, Base64.getUrlDecoder().decode(either.get().headers().get(AplicationConstants.PUBLIC_KEY)));
                                            publicKeyFuture.complete(pk);
                                        } catch (Exception e) {
                                            log.error(e.getMessage(), e);
                                            publicKeyFuture.completeExceptionally(e);
                                        }
                                    })
                                    .peekLeft(error -> {
                                        log.error(error);
                                        publicKeyFuture.completeExceptionally(new RuntimeException(error));
                                    });
                        }
                    });
            return publicKeyFuture.join();
        }
    }
}
