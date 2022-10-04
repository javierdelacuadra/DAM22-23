package data;

import config.ConfigTXT;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Newspaper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

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
        Path path = Paths.get(configTXT.getPathNewspapers());
        ArrayList<Newspaper> newspapers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> {
                newspapers.add(new Newspaper(line));
            });
        } catch (IOException e) {
            log.error("Error", e);
        }
        return newspapers;
    }

    public boolean saveNewspaper(Newspaper newspaper) {
        String line = newspaper.toLine();
        Path path = Paths.get(configTXT.getPathNewspapers());
        try {
            Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            log.error("Error", e);
            return false;
        }
    }

    public boolean deleteNewspaper(Newspaper newspaper) {
        String line = newspaper.toLine();
        Path path = Paths.get(configTXT.getPathNewspapers());
        try {
            List<String> lines = Files.readAllLines(path);
            lines.remove(line);
            Files.write(path, lines);
            return true;
        } catch (IOException e) {
            log.error("Error ", e);
            return false;
        }
    }

    public boolean checkArticles(Newspaper newspaper) {
        return daoArticle.getArticles().stream().anyMatch(article -> article.getIdNewspaper() == newspaper.getId());
    }
}