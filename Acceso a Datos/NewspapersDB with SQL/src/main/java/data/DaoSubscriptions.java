package data;

import common.Constantes;
import data.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoSubscriptions {
    private final DBConnection db;

    @Inject
    public DaoSubscriptions(DBConnection db) {
        this.db = db;
    }


}