package dao.common;

public class DAOConstants {

    public static final String TRUNCATED_INCORRECT_DOUBLE_VALUE = "Truncated incorrect DOUBLE value:";
    public static final String NO_SE_HA_ENCONTRADO_EL_LECTOR_CON_ID = "No se ha encontrado el lector con id: ";
    public static final String PARA_ELIMINARLO = " para eliminarlo.";
    public static final String NO_SE_HA_PODIDO_CREAR_EL_LECTOR = "No se ha podido crear el lector.";
    public static final String NO_EXISTE_UN_LECTOR_CON_EL_ID = "No existe un lector con el id ";
    public static final String NO_SE_PUEDE_MODIFICAR_EL_LECTOR = "Ya existe un reader con el mismo username. Inténtelo de nuevo.";

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
