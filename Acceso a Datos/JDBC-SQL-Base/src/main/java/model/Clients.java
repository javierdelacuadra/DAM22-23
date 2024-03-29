package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@XmlRootElement(name = "clients")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class Clients {

    @XmlElement(name = "client")
    List<Client> client;
}
