package modelo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement(name = "readArticles")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class ReadArticle {
    private int id;
    private int id_reader;
    private int id_article;
    private int rating;
}
