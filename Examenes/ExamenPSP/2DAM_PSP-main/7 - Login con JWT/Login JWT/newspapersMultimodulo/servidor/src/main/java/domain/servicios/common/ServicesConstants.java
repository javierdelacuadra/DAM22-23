package domain.servicios.common;

public class ServicesConstants {

    public static final String NO_SE_PUDO_AGREGAR_EL_LECTOR = "No se pudo agregar el lector";
    public static final String MAIL_BIENVENIDA = "<a href=\"http://localhost:8080/newspapers-1.0-SNAPSHOT/activate?token=%s\"> Validate aqui! </a> <br/><br/><br/> Si ha caducado el tiempo para validarte pincha <a href=\"http://localhost:8080/newspapers-1.0-SNAPSHOT/resend?token=%s\" > aquí! </a>";
    public static final String RE_VALIDAR_USER_NEWSPAPERS = "RE VALIDAR USER NEWSPAPERS";
    public static final String MAIL_RESET_PASS = "<a href=\"http://localhost:8080/newspapers-1.0-SNAPSHOT/reset?token=%s\" > Cambia tu contraseña aquí! </a>";
    public static final String BIENVENIDO_A_LOS_NEWSPAPERS = "BIENVENIDO A LOS NEWSPAPERS!";
    public static final String NO_SE_PUDO_ENVIAR_EL_MAIL_A = "No se pudo enviar el mail a ";
    public static final String NO_EXISTE_EL_USUARIO_CON_EL_EMAIL = "No existe el usuario con el email ";

    private ServicesConstants() {
    }

    public static final String NO_HAY_UN_NEWSPAPER_CON_EL_ID = "No hay un newspaper con el id ";
    public static final String THE_NEW_READER_MUST_BE_AT_LEAST_1_YEARS_OLD = "The new reader must be at least 1 year old!";
    public static final String PLEASE_FILL_ALL_THE_FIELDS = "Please fill all the fields";
    public static final String REMEMBER_THAT_THE_CREDENTIALS_CAN_NOT_BE_LONGER_THAN_15_CHARACTERS = "Remember that the password can not be longer than 15 characters";
    public static final String THE_RELEASE_DATE_CANNOT_BE_IN_THE_FUTURE = "The release date cannot be in the future";
}
