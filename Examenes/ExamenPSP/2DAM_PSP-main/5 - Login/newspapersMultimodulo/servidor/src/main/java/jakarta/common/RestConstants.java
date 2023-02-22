package jakarta.common;

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

    public static final String PATH_LOGIN = "/login";


    public static final String NO_SE_ENCONTRO_EL_LECTOR_CON_EL_ID = "No se encontro el lector con el id: ";
    public static final String NO_SE_ENCONTRO_EL_NEWSPAPER_CON_EL_ID = "No se encontro el newspaper con el id: ";
    public static final String TIPO = "tipo";
    public static final String ID = "id";
    public static final String HEADER_PARAM_INVALIDO = "Header param invalido. ";
    public static final String TIENE_QUE_HABER_UN_PARAMETRO_EMAIL_PARA_EL_CORREO_Y_PASS_PARA_LA_CONTRSENA = "Tiene que haber un parametro email para el correo y pass para la contrseña";
    public static final String EMAIL = "email";
    public static final String PASS = "pass";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "/logout";
    public static final String PATH_FORGOT = "/forgot";
    public static final String EL_USUARIO_O_LA_CONTRASENA_SON_INCORRECTOS = "El usuario o la contraseña son incorrectos";
    public static final String NO_EXISTE_EL_USUARIO_CON_EL_MAIL = "No existe el usuario con el mail ";
    public static final String TOKEN = "token";
    public static final String RESET = "reset";
    public static final String NEW_PASS = "newPass";
    public static final String MENSAJE_EXITO = "mensajeExito";
    public static final String SE_HA_REINICIADO_LA_CONTRASENA_YA_PUEDES_ACCEDER_CON_TU_NUEVA_CONTRASENA = "Se ha reiniciado la contraseña. Ya puedes acceder con tu nueva contraseña.";
    public static final String EXITO = "exito";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String NO_SE_HA_PODIDO_VALIDAR_EL_TOKEN = "No se ha podido validar el token";
    public static final String ERROR = "error";
    public static final String SERVLET_RESET = "ServletReset";
    public static final String PATH_RESET = "/reset";
    public static final String INDEX = "index";
    public static final String SE_HA_RENOVADO_EL_TIEMPO_DE_VALIDACION_SE_HA_ENVIADO_UN_MAIL_PARA_QUE_TE_VALIDES = "Se ha renovado el tiempo de validación. Se ha enviado un mail para que te valides.";
    public static final String PATH_RESEND = "/resend";
    public static final String RESEND_SERVLET = "ResendServlet";
    public static final String CONFIRM_SERVLET = "ConfirmServlet";
    public static final String PATH_ACTIVATE = "/activate";
    public static final String CUENTA_VALIDADA_CON_EXITO_YA_PUEDES_HACER_LOGIN_NORMALMENTE = "Cuenta validada con exito. Ya puedes hacer login normalmente.";


    private RestConstants() {
    }


}
