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

public class DaoNewspaperSQL {
    private final DBConnection db;

    @Inject
    public DaoNewspaperSQL(DBConnection db) {
        this.db = db;
    }

    public Either<Integer, List<Newspaper>> getAll() {
        List<Newspaper> newspapers = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_NEWSPAPERS);
            newspapers = readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newspapers.isEmpty() ? Either.left(-1) : Either.right(newspapers);
    }

    private List<Newspaper> readRS(ResultSet rs) {
        List<Newspaper> newspapers = new ArrayList<>();
        try {
            while (rs.next()) {
                Newspaper newspaper = new Newspaper();
                newspaper.setId(rs.getInt(Constantes.ID));
                newspaper.setName(rs.getString(Constantes.NAME));
                newspaper.setReleaseDate(String.valueOf(rs.getDate(Constantes.RELEASE_DATE)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoReadersSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newspapers;
    }
}