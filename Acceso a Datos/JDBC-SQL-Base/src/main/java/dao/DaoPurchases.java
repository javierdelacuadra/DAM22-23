package dao;

import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Purchase;
import model.PurchasesItem;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPurchases {
    private final DBConnection db;
    private final DaoItems daoItems;
    private final DaoPurchasesItems daoPurchasesItems;

    @Inject
    public DaoPurchases(DBConnection db, DaoItems daoItems, DaoPurchasesItems daoPurchasesItems) {
        this.db = db;
        this.daoItems = daoItems;
        this.daoPurchasesItems = daoPurchasesItems;
    }

    public int save(Purchase purchase) {
        AtomicInteger result = new AtomicInteger();
        Connection con = null;
        try {
            con = db.getConnection();
        } catch (SQLException e) {
            result.set(-2);
        }
        try {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(SQLQueries.INSERT_PURCHASE, Statement.RETURN_GENERATED_KEYS)) {
                con.setAutoCommit(false);
                ps.setInt(1, purchase.getId_client());
                ps.setDate(2, Date.valueOf(purchase.getP_date()));
                ps.setDouble(3, daoItems.checkTotalCost(purchase.getPurchases()));
                ps.setInt(4, purchase.getPaid());
                result.set(ps.executeUpdate());
                ResultSet rs = ps.getGeneratedKeys();
                int idPurchase = 0;
                if (rs.next()) {
                    idPurchase = rs.getInt(1);
                }
                if (purchase.getPurchases() != null) {
                    List<PurchasesItem> purchasesItems = purchase.getPurchases();
                    Connection finalCon = con;
                    int finalIdPurchase = idPurchase;
                    purchasesItems.forEach(purchasesItem -> {
                                try (PreparedStatement ps2 = finalCon.prepareStatement(SQLQueries.INSERT_PURCHASES_ITEMS)) {
                                    ps2.setInt(1, finalIdPurchase);
                                    ps2.setInt(2, purchasesItem.getId_item());
                                    ps2.setInt(3, purchasesItem.getAmount());
                                    ps2.executeUpdate();
                                } catch (SQLException e) {
                                    result.set(-1);
                                }
                            }
                    );
                }
                con.commit();
                result.set(1);
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
                result.set(-3);
            } catch (SQLException e) {
                Logger.getLogger(DaoPurchases.class.getName()).log(Level.SEVERE, null, ex);
                result.set(-3);
            }
        }
        return result.get();
    }

    //payPurchase
    public int update(int clientID, int purchaseID) {
        int result;
        List<PurchasesItem> purchasesItems = daoPurchasesItems.getAll(purchaseID);
        double totalCost = daoItems.checkTotalCost(purchasesItems);
        double clientBalance = daoItems.checkClientBalance(clientID);
        if (clientBalance > totalCost) {
            double newBalance = clientBalance - totalCost;
            Connection con;
            try {
                con = db.getConnection();
            } catch (SQLException e) {
                return -2;
            }
            try {
                assert con != null;
                try (PreparedStatement ps = con.prepareStatement(SQLQueries.UPDATE_BALANCE)) {
                    ps.setDouble(1, newBalance);
                    ps.setInt(2, clientID);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps2 = con.prepareStatement(SQLQueries.UPDATE_PURCHASE_STATUS)) {
                    ps2.setInt(1, 1);
                    ps2.setInt(2, purchaseID);
                    ps2.executeUpdate();
                }
                result = 1;
            } catch (SQLException ex) {
                Logger.getLogger(DaoPurchases.class.getName()).log(Level.SEVERE, null, ex);
                return -3;
            }
        } else {
            result = -1;
        }
        return result;
    }

    //updatePurchase
    public int update(PurchasesItem purchasesItem) {
        int result = -1;
        List<PurchasesItem> purchasesItems = daoPurchasesItems.getAll(purchasesItem.getId_purchase());
        purchasesItems.forEach(purchasesItem1 -> {
                    if (purchasesItem1.getId() == purchasesItem.getId()) {
                        purchasesItem1.setAmount(purchasesItem.getAmount());
                    }
                }
        );
        double newTotalCost = daoItems.checkTotalCost(purchasesItems);
        try {
            String query = SQLQueries.UPDATE_PURCHASED_ITEM;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            jdbc.update(query, purchasesItem.getAmount(), purchasesItem.getId());
            result = 1;
        } catch (DataAccessException e) {
            Logger.getLogger(DaoPurchases.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            String query = SQLQueries.UPDATE_TOTAL_COST;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            jdbc.update(query, newTotalCost, purchasesItem.getId_purchase());
            result = 1;
        } catch (DataAccessException e) {
            Logger.getLogger(DaoPurchases.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public Either<Integer, List<Purchase>> getAll() {
        List<Purchase> clients = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_PURCHASES;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            clients = jdbc.query(query, BeanPropertyRowMapper.newInstance(Purchase.class));
        } catch (DataAccessException e) {
            Logger.getLogger(DaoPurchases.class.getName()).log(Level.SEVERE, null, e);
        }
        return clients.isEmpty() ? Either.left(-1) : Either.right(clients);
    }

}