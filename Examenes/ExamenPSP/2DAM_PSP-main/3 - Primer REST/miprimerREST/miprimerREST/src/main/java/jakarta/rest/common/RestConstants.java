package jakarta.rest.common;

public class RestConstants {


    //TENDRIA QUE CREAR  UN FICHERO DE CONFIGURACION Y ACCEDER DESDE JAKARTA? NO TIENE MUCHO SENTIDO NO?
    public static final String PATH_NEWS = "/news";
    public static final String PATH_PARAM_TIPO = "/{tipo}";
    public static final String PATH_TIPO1 = "/tipo";
    public static final String PATH_OLDEST = "/oldest";
    public static final String PATH_READERS = "/readers";
    public static final String PATH_SUSCRIPCIONES = "/suscripciones";
    public static final String PATH_LOGIN = "/login";
    public static final String PATH_READARTS = "/readarts";

    public static final String PATH_TYPES = "/types";
    public static final String PATH_ARTICLES = "/articles";
    public static final String PATH_TYPE = "/type";

    public static final String PATH_PARAM_ID = "/{id}";
    public static final String PATH_NEWSPAPERS = "/newspapers";
    public static final String PATH_UPDATE = "/update";

    public static final String NO_SE_ENCONTRO_EL_LECTOR_CON_EL_ID = "No se encontro el lector con el id: ";
    public static final String NO_SE_ENCONTRO_EL_NEWSPAPER_CON_EL_ID = "No se encontro el newspaper con el id: ";
    public static final String QUERY_PARAM_INVALIDO = "Query param invalido. ";
    public static final String EL_VALOR_DE_SUSC_DEBE_SER_TRUE_O_FALSE = "El valor de susc debe ser true o false.";

    public static final String TIENE_QUE_HABER_UN_PARAMETRO_USER_PARA_EL_USUARIO_Y_PASS_PARA_LA_CONTRSENA = "Tiene que haber un parametro user para el usuario y pass para la contrseña";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String SUSC = "susc";
    public static final String USER = "user";
    public static final String PASS = "pass";
    public static final String TIPO = "tipo";
    public static final String ID = "id";
    public static final String EL_PARAMETRO_ART_ID_DEBE_SER_EL_ID_DEL_ARTICULO_QUE_HA_LEIDO_EL_LECTOR = "El parámetro artID debe ser el id del artículo que ha leído el lector ";
    public static final String ART_ID = "artID";
    public static final String DESCANDREADERS = "/descandreaders";
    public static final String NAMEARTANDNEWS = "/nameartandnews";


    private RestConstants() {
    }


}
