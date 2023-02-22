package com.serverql.graphqlserver.domain.servicios.common;

public class ServicesConstants {

    public static final String LA_PERSONA_YA_EXISTE = "La persona ya existe, prueba otro username";
    public static final String LA_MASCOTA_NO_EXISTE = "La mascota no existe";

    public static final String LA_MASCOTA_NO_ES_EDITABLE = "No puedes editar la mascota porque no existe o no es tuya";

    public static final String LA_MASCOTA_NO_ES_ELIMINABLE = "No puedes eliminar la mascota porque no existe o no es tuya";



    private ServicesConstants() {
    }
    public static final String EL_ARTICULO_NO_EXISTE = "El articulo no existe";
    public static final String NO_SE_PUEDE_CREAR_EL_ARTICULO_PORQUE_EL_NEWSPAPER_NO_EXISTE = "No se puede crear el articulo porque el newspaper no existe";
    public static final String LA_PERSONA_NO_EXISTE = "La persona no existe";
    public static final String NO_SE_PUEDE_ELIMINAR_LA_PERSONA_PORQUE_TIENE_MASCOTAS_ASOCIADAS = "No se puede eliminar la persona porque tiene mascotas asociadas";
}
