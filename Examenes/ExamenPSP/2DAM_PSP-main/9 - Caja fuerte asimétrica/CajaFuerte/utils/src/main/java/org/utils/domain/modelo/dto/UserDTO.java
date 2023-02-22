package org.utils.domain.modelo.dto;

import lombok.Data;
import org.utils.domain.modelo.Firma;

@Data
public class UserDTO {
    private String name;
    private Firma firma;

    public UserDTO(String name, Firma firma) {
        this.name = name;
        this.firma = firma;
    }

    public UserDTO() {
    }


    public String toJsonString() {
        return "{" +
                "\"name\": \"" + name + "\", " +
                "\"firma\": " + firma.toJsonString() +
                '}';
    }
}
