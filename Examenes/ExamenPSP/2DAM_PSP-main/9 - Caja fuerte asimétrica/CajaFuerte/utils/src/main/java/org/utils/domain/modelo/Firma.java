package org.utils.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Firma {
    private String textoOriginal;
    private String textoFirmado;




    public String toJsonString() {
        return "{" +
                "\"textoOriginal\": \"" + textoOriginal + "\", " +
                "\"textoFirmado\": \"" + textoFirmado + "\"" +
                '}';
    }
}
