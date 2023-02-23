package data.retrofit.common;

public class ConstantesAPI {

    public ConstantesAPI() {
    }

    public static final String AUTHORIZATION = "Authorization";
    public static final String ACCION_NO_AUTORIZADA = "Acción no autorizada";
    public static final String ERROR_403 = "No estás autorizado para realizar esta acción o tu token ha expirado";
    public static final String ERROR_429 = "Se han realizado demasiadas peticiones\nInténtelo de nuevo más tarde";
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String LOGIN_PASSWORD_RECOVERY = "login/passwordRecovery";
    public static final String EMAIL = "email";
    public static final String LOGIN_EMAIL_RESEND = "login/emailResend";
    public static final String LOGIN_LOGOUT = "login/logout";
    public static final String CARPETAS_PATH = "carpetas";
    public static final String CARPETAS_ID_PATH = "carpetas/{id}";
    public static final String NOMBRE_CARPETA = "nombreCarpeta";
    public static final String PASSWORD_CARPETA = "passwordCarpeta";
    public static final String NOMBRE_USUARIO = "nombreUsuario";
    public static final String MENSAJES_PATH = "mensajes";
    public static final String MENSAJES_ID_PATH = "mensajes/{id}";
    public static final String USUARIOS_PATH = "usuarios";
}
