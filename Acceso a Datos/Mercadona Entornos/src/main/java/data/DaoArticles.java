package data;

import config.Configuracion;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Article;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoArticles {

    private final Configuracion configuracion;

    @Inject
    public DaoArticles(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public List<Article> getArticles() {
        Path path = Paths.get(configuracion.getPathArticles());
        ArrayList<Article> articles = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> {
                articles.add(new Article(line));
            });
        } catch (IOException e) {
            log.error("Error al leer el archivo de articulos", e);

        }
        return articles;
    }

    public boolean saveArticle(Article article) {
        String line = article.toLine();
        Path path = Paths.get(configuracion.getPathArticles());
        try {
            Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            log.error("Error al guardar el articulo", e);
            return false;
        }
    }

    public boolean deleteArticle(String article) {
        Path path = Paths.get(configuracion.getPathArticles());
        try {
            List<String> lines = Files.readAllLines(path);
            lines.remove(article);
            Files.write(path, lines);
            return true;
        } catch (IOException e) {
            log.error("Error al eliminar el articulo", e);
            return false;
        }
    }

}