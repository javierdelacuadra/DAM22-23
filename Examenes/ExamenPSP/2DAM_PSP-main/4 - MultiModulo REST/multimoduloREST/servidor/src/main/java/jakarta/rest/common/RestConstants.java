package jakarta.rest.common;

public class RestConstants {


    //TENDRIA QUE CREAR  UN FICHERO DE CONFIGURACION Y ACCEDER DESDE JAKARTA? NO TIENE MUCHO SENTIDO NO?
    public static final String PATH_NEWS = "/news";
    public static final String PATH_PARAM_TIPO = "/{tipo}";
    public static final String PATH_TIPO = "/tipo";
    public static final String PATH_OLDEST = "/oldest";
    public static final String PATH_READERS = "/readers";
    public static final String PATH_PARAM_ID = "/{id}";
    public static final String PATH_NEWSPAPERS = "/newspapers";
    public static final String PATH_UPDATE = "/update";
    public static final String PATH_TYPES = "/types";

    public static final String NO_SE_ENCONTRO_EL_LECTOR_CON_EL_ID = "No se encontro el lector con el id: ";
    public static final String NO_SE_ENCONTRO_EL_NEWSPAPER_CON_EL_ID = "No se encontro el newspaper con el id: ";
    public static final String TIPO = "tipo";
    public static final String ID = "id";


    private RestConstants() {
    }


}
