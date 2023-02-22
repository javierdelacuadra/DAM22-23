package common;

public class NumericConstants {

    public static final int DB_EXCEPTION_CODE = -3;
    public static final int NON_RELATED_TO_DB_EXCEPTION_CODE = -2;
    public static final int SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION = 1062;    // 1062 is the code for the exception when a duplicate key is inserted in the DB
    public static final int DATA_INTEGRITY_VIOLATION_EXCEPTION = 1451;             // 1451 is the code for the exception when a trying to delete a record that is referenced in another table
    public static final int UPDATED_RECORD_EXCEPTION = 1452;                       // 1452 is the code for the exception when a trying to insert a record but the referenced record is not found
}
