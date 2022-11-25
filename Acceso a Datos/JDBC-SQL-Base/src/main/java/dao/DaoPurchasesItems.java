package dao;

import dao.common.SQLQueries;
import jakarta.inject.Inject;
import model.PurchasesItem;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPurchasesItems {

    private final DBConnection db;

    @Inject
    public DaoPurchasesItems(DBConnection db) {
        this.db = db;
    }

    public List<PurchasesItem> getAll(int id) {
        List<PurchasesItem> purchasesItems = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_PURCHASE_ITEMS_BY_ID;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            purchasesItems = jdbc.query(query, BeanPropertyRowMapper.newInstance(PurchasesItem.class), id);
        } catch (DataAccessException e) {
            Logger.getLogger(DaoPurchases.class.getName()).log(Level.SEVERE, null, e);
        }
        return purchasesItems;
    }
}
