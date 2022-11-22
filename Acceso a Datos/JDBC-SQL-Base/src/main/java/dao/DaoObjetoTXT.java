package dao;

import config.ConfigTXT;
import dao.common.Constantes;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Objeto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class DaoObjetoTXT {
    private final ConfigTXT configTXT;

    @Inject
    public DaoObjetoTXT(ConfigTXT configTXT) {
        this.configTXT = configTXT;
    }

    public List<Objeto> getObjetos() {
        Path path = Paths.get(configTXT.getPathObjetos());
        ArrayList<Objeto> objetos = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> objetos.add(new Objeto(line)));
        } catch (IOException e) {
            log.error(Constantes.ERROR_READING_TXT, e);

        }
        return objetos;
    }

    public boolean saveObjeto(Objeto objeto) {
        AtomicBoolean objetoIDUnique = new AtomicBoolean(true);
        getObjetos().forEach(a -> {
            if (a.getId() == objeto.getId()) {
                objetoIDUnique.set(false);
            }
        });
        if (objetoIDUnique.get()) {
            String line = objeto.toLine();
            Path path = Paths.get(configTXT.getPathObjetos());
            try {
                Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
                return true;
            } catch (IOException e) {
                log.error(Constantes.ERROR_SAVING_TXT, e);
                return false;
            }
        }
        return false;
    }
}