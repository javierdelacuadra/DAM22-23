package dao.common;

public class DAOConstants {

    public static final String TRUNCATED_INCORRECT_DOUBLE_VALUE = "Truncated incorrect DOUBLE value:";
    public static final String NO_SE_HA_ENCONTRADO_EL_LECTOR_CON_ID = "No se ha encontrado el lector con id: ";
    public static final String PARA_ELIMINARLO = " para eliminarlo.";
    public static final String NO_SE_HA_PODIDO_CREAR_EL_LECTOR = "No se ha podido crear el lector.";
    public static final String NO_EXISTE_UN_LECTOR_CON_EL_ID = "No existe un lector con el id ";
    public static final String YA_EXISTE_UN_READER_CON_ESE_MAIL = "Ya existe un reader con el mismo mail. Inténtelo de nuevo.";
    public static final String INVALID_USER_OR_PASSWORD = "El usuario y la contraseña no coinciden.";
    public static final String USER_INACTIVE = "El usuario no está activado aún. Por favor, revisa tu correo.";
    public static final String NOT_REGISTERED_USER = "No existe un usuario con ese mail";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String TEXT_HTML = "text/html";
    public static final String NO_SE_ENCONTRO_UN_USUARIO_CON_ESE_TOKEN_ESTAS_SEGURO_QUE_ES_CORRECTO = "No se encontro un usuario con ese token ¿Estás seguro que es correcto?";
    public static final String AUN_NO_HA_CADUCADO_EL_TIEMPO_PARA_VALIDAR_ESTA_CUENTA_PRUEBA_A_VALIDARTE_DESDE_TU_MAIL = "Aún no ha caducado el tiempo para validar esta cuenta, prueba a validarte desde tu mail.";
    public static final String EL_USUARIO_YA_HA_SIDO_VALIDADO_PREVIAMENTE_DEBERIAS_PODER_HACER_LOGIN_NORMALMENTE = "El usuario ya ha sido validado previamente. Deberías poder hacer login normalmente.";
    public static final String EL_TIEMPO_PARA_VALIDAR_ESTA_CUENTA_HA_CADUCADO_VUELVE_A_GENERAR_LA_CONTRASENA = "El tiempo para validar esta cuenta ha caducado. Vuelve a generar la contraseña.";
    public static final String NO_SE_ENCONTRO_UN_USUARIO_CON_ESE_EMAIL_ESTAS_SEGURO_QUE_ES_CORRECTO = "No se encontro un usuario con ese email ¿Estás seguro que es correcto?";
    public static final String CAMBIA_TU_CONTRASENA_NEWSPAPERS = "CAMBIA TU CONTRASEÑA NEWSPAPERS";
    public static final String LIMITE_DE_PETICIONES_SUPERADO = "Límite de peticiones superado. Vuelve a intentarlo dentro de ";

    private DAOConstants() {
    }

    public static final String ERROR_GETTING_NEWSPAPERS = "ERROR GETTING NEWSPAPERS";

    public static final String NON_RELATED_WITH_THE_DB = " NON RELATED WITH THE DB";

    public static final String SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION = "SQLIntegrityConstraintViolationException";
    public static final String ERROR_UPDATING_NEWSPAPER = "Error updating newspaper. ";
    public static final String ERROR_ADDING_NEWSPAPER = "Error adding newspaper. ";

    public static final String YA_HAY_UN_NEWSPAPER_CON_ESE_NOMBRE_Y_FECHA_DE_PUBLICACION = "Ya hay un newspaper con ese nombre y fecha de publicación";
    public static final String ERROR_DELETING_NEWSPAPER = "Error deleting newspaper.";
    public static final String ROLLBACK_DONE = " Rollback done.";
    public static final String NO_SE_HA_ENCONTRADO_EL_PERIODICO_CON_ID = "No se ha encontrado el periódico con id ";
    public static final String EL_ID_DEL_NEWSPAPER_ES_INCORRECTO = " El id del newspaper no es numérico";
}
