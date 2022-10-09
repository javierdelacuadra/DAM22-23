package data;

import common.Constantes;
import config.ConfigTXT;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Article;
import modelo.Newspaper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class DaoArticles {

    private final ConfigTXT configTXT;

    @Inject
    public DaoArticles(ConfigTXT configTXT) {
        this.configTXT = configTXT;
    }

    public List<Article> getArticles() {
        Path path = Paths.get(configTXT.getPathArticles());
        ArrayList<Article> articles = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> articles.add(new Article(line)));
        } catch (IOException e) {
            log.error(Constantes.COULD_NOT_READ_THE_ARTICLES, e);

        }
        return articles;
    }

    public List<Newspaper> getNewspapers() {
        Path path = Paths.get(configTXT.getPathNewspapers());
        ArrayList<Newspaper> newspapers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> newspapers.add(new Newspaper(line)));
        } catch (IOException e) {
            log.error(Constantes.COULD_NOT_READ_THE_NEWSPAPERS, e);
        }
        return newspapers;
    }

    public boolean saveArticle(Article article) {
        if (checkArticle(article)) {
            String line = article.toLine();
            Path path = Paths.get(configTXT.getPathArticles());
            try {
                Files.write(path, line.getBytes(), StandardOpenOption.APPEND);
                return true;
            } catch (IOException e) {
                log.error(Constantes.COULD_NOT_SAVE_THE_ARTICLE, e);
                return false;
            }
        }
        return false;
    }

    public boolean checkArticle(Article article) {
        AtomicBoolean articleIDUnique = new AtomicBoolean(true);
        getArticles().forEach(a -> {
            if (a.getId() == article.getId()) {
                articleIDUnique.set(false);
            }
        });
        AtomicBoolean newspaperIDExists = new AtomicBoolean(false);
        getNewspapers().forEach(n -> {
            if (n.getId() == article.getIdNewspaper()) {
                newspaperIDExists.set(true);
            }
        });
        return articleIDUnique.get() && newspaperIDExists.get();
    }
}