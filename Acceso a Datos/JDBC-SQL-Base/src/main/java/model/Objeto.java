package model;

import di.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@XmlRootElement(name = "objeto")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class Objeto {

    public int id;

    public String nombre;

    public String apellido;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlElement(name = "fecha")
    public LocalDate fecha;

    public Objeto(String line) {
        String[] split = line.split(";");
        this.id = Integer.parseInt(split[0]);
        this.nombre = split[1];
        this.apellido = split[2];
        this.fecha = LocalDate.parse(split[3]);
    }

    public String toLine() {
        return id + ";" + nombre + ";" + apellido + ";" + fecha + " ";
    }
}