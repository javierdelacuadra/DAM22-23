package data;

import common.Constantes;
import config.ConfigTXT;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Article;
import model.Newspaper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class DaoNewspaper {

    private final ConfigTXT configTXT;
    private final DaoArticles daoArticle;

    @Inject
    public DaoNewspaper(ConfigTXT configTXT, DaoArticles daoArticle) {
        this.configTXT = configTXT;
        this.daoArticle = daoArticle;
    }

    public List<Newspaper> getNewspapers() {
        Path newspaperPath = Paths.get(configTXT.getPathNewspapers());
        List<Newspaper> newspapers = new ArrayList<>();
        try {
            List<String> newspaperLines = Files.readAllLines(newspaperPath);
            newspaperLines.forEach(line -> newspapers.add(new Newspaper(line)));
            return newspapers;
        } catch (IOException e) {
            log.error(Constantes.COULD_NOT_READ_THE_NEWSPAPERS, e);
            return Collections.emptyList();
        }
    }

    public void deleteNewspaper(Newspaper newspaper) {
        String line = newspaper.toLine();
        Path path = Paths.get(configTXT.getPathNewspapers());
        try {
            List<String> lines = Files.readAllLines(path);
            lines.remove(line);
            Files.write(path, lines);
        } catch (IOException e) {
            log.error(Constantes.THE_NEWSPAPER_COULD_NOT_BE_DELETED, e);
        }
    }

    public boolean checkArticles(Newspaper newspaper) {
        List<Article> articles = daoArticle.getArticles();
        AtomicBoolean result = new AtomicBoolean(false);
        articles.forEach(article -> {
            if (article.getIdNewspaper() == newspaper.getId()) {
                result.set(true);
            }
        });
        return result.get();
    }
}