package dao.common;

public class DAOConstants {

    public static final String TRUNCATED_INCORRECT_DOUBLE_VALUE = "Truncated incorrect DOUBLE value:";
    public static final String NO_SE_HA_ENCONTRADO_EL_LECTOR_CON_ID = "No se ha encontrado el lector con id: ";
    public static final String PARA_ELIMINARLO = " para eliminarlo.";
    public static final String NO_SE_HA_PODIDO_CREAR_EL_LECTOR = "No se ha podido crear el lector.";
    public static final String NO_EXISTE_UN_LECTOR_CON_EL_ID = "No existe un lector con el id ";
    public static final String THE_READER_NEWS_DONT_EXIST_OR_THE_READER_ALREADY_SUSCRIBED_TO_THIS_NEWSPAPER = "The reader/news dont exist or the reader already suscribed to this newspaper";

    private DAOConstants() {
    }

    public static final String ERROR_GETTING_NEWSPAPERS = "ERROR GETTING NEWSPAPERS OF ";

    public static final String NON_RELATED_WITH_THE_DB = " NON RELATED WITH THE DB";

    public static final String SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION = "SQLIntegrityConstraintViolationException";
    public static final String ERROR_UPDATING_NEWSPAPER = "Error updating newspaper. ";
    public static final String ERROR_ADDING_NEWSPAPER = "Error adding newspaper. ";

    public static final String YA_HAY_UN_NEWSPAPER_CON_ESE_NOMBRE_Y_FECHA_DE_PUBLICACION = "Ya hay un newspaper con ese nombre y fecha de publicación";
    public static final String ERROR_DELETING_NEWSPAPER = "Error deleting newspaper.";
    public static final String ROLLBACK_DONE = " Rollback done.";
    public static final String NO_SE_HA_ENCONTRADO_EL_PERIODICO_CON_ID = "No se ha encontrado el periódico con id ";
    public static final String EL_ID_DEL_NEWSPAPER_ES_INCORRECTO = " El id del newspaper no es numérico";
    public static final String INVALID_USER_OR_PASSWORD = "Invalid user or password";
    public static final String THE_USER = "The user ";
    public static final String HAS_NOT_RATED_THE_ARTICLE = " has not rated the article ";
    public static final String INCORRECT_RESULT_SIZE_EXPECTED_1_ACTUAL_0 = "Incorrect result size: expected 1, actual 0";
    public static final String HAS_NO_BAD_RATED_ARTICLES = " has no bad rated articles";
    public static final String THE_NEWSPAPER = "The newspaper ";
    public static final String HAS_NOT_READ_THE_ARTICLE = " has not read the article ";
    public static final String EL_ID_DEL_READER_Y_O_DEL_ARTICULO_NO_SON_VALIDOS = "El id del reader y/o del artículo no son válidos.";
    public static final String THE_READER = "The reader ";
    public static final String HAS_ALREADY_READ_THE_ARTICLE = " has already read the article ";
    public static final String THERE_ARE_NO_ARTICLES_IN_THE_NEWSPAPER = "There are no articles in the newspaper ";
    public static final String THERE_ARE_NO_ARTICLES_OF_TYPE = "There are no articles with the id type = ";
    public static final String THERE_ARE_NO_ARTICLES_WITH_ID = "There are no articles with id ";
    public static final String ARTICLE_ALREADY_EXISTS = "There already is an article with the same name and type in the newspaper";
}
