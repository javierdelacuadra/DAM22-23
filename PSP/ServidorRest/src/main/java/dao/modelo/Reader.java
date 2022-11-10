package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {

    private int id;

    private String name;

    private LocalDate dateOfBirth;

//    @XmlElementWrapper(name = "subscriptions")
//    @XmlElement(name = "subscription")
//    private List<Subscription> subscriptions;
//
//
//    @XmlElementWrapper(name = "readArticles")
//    @XmlElement(name = "readArticle")
//    private List<ReadArticle> readArticles;

}
