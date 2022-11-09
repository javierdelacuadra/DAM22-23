package dao.modelo;

import di.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@XmlRootElement(name = "reader")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class Reader {

    private int id;

    private String name;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlElement(name = "dateOfBirth")
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
