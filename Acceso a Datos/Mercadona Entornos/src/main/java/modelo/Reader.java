package modelo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@XmlRootElement
public class Reader {
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private String birthDate;
    private List<Subscription> subscriptions;
    private List<Article> articlesRead;
}
