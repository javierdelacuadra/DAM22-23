package config;

import common.Constantes;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Getter
@Log4j2
@Singleton
public class ConfigTXT {

    private final String pathArticles;
    private final String pathNewspapers;
    private final String pathArticleTypes;

    private ConfigTXT() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("config.yaml");
        Map<String, Object> obj = yaml.load(inputStream);
        this.pathArticles = (String) obj.get(Constantes.PATH_ARTICLES);
        this.pathNewspapers = (String) obj.get(Constantes.PATH_NEWSPAPERS);
        this.pathArticleTypes = (String) obj.get(Constantes.PATH_ARTICLE_TYPES);
    }
}