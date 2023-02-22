package dao.common;

public class ConstantesDAO {


    private ConstantesDAO() {
    }

    public static final String X_CSCAPI_KEY = "X-CSCAPI-KEY";
    public static final String ERROR_AL_OBTENER_LOS_ESTADOS_DE = "Error al obtener los estados de ";
    public static final String EL_PAIS_NO_EXISTE_AUN = "El país no existe aún";
    public static final String ERROR_AL_OBTENER_LOS_PAISES = "Error al obtener los paises";
    public static final String ERROR_AL_OBTENER_LAS_CIUDADES_DE = "Error al obtener las ciudades de ";
    public static final String COMA = ", ";
    public static final String BREAK = "\n";
    public static final String ERROR_EN_LA_REQUEST = "Error en la respuesta de la request";
    public static final String SPACE = " ";
    public static final String NO_TIENE_ESTADOS = "no tiene Estados";
    public static final String NO_TIENE_CIUDADES = "no tiene ciudades";

    public static final String NO_HAY_PAISES = "No se encontraron países";


    // VARIABLES OBTENIDAS DE LA API
    public static final String CURRENCY_NAME = "currency_name";
    public static final String CURRENCY_SYMBOL = "currency_symbol";
    public static final String NUMERIC_CODE = "numeric_code";

    //LLAMADAS Y ATRIBUTOS A RETROFIT

    public static final String COUNTRIES = "countries";
    public static final String COUNTRIES_COUNTRY_ISO_STATES = "countries/{countryISO}/states";
    public static final String COUNTRIES_COUNTRY_ISO_STATES_STATE_ISO_CITIES = "countries/{countryISO}/states/{stateISO}/cities";
    public static final String COUNTRIES_COUNTRY_ISO = "countries/{countryISO}";
    public static final String COUNTRY_ISO = "countryISO";
    public static final String STATE_ISO = "stateISO";


}
