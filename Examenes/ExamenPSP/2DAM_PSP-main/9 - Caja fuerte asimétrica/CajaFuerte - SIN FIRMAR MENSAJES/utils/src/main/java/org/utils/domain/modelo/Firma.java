package org.utils.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Firma {
    private String textoFirmado;
    private String srFirmado;

    @Override
    public String toString() {
        return "{" +
                "\"textoFirmado\": \"" + textoFirmado + "\", " +
                "\"srFirmado\": \"" + srFirmado + "\"" +
                '}';
    }
}
