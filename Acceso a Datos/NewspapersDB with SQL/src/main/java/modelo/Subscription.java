package modelo;

import di.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@XmlRootElement(name = "subscriptions")
@XmlAccessorType(XmlAccessType.FIELD)
public class Subscription {
    private int id;
    private int id_reader;
    private int id_newspaper;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate signingDate;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate cancellationDate;
}