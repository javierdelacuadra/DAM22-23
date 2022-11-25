package dao;

import dao.common.SQLQueries;
import jakarta.inject.Inject;
import model.MostPurchasedItem;
import model.PurchasesItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoItems {

    private final DBConnection db;

    @Inject
    public DaoItems(DBConnection db) {
        this.db = db;
    }

    public double checkTotalCost(List<PurchasesItem> items) {
        AtomicReference<Double> totalCost = new AtomicReference<>((double) 0);
        items.forEach(purchasesItem -> {
                    try (Connection con = db.getConnection();
                         PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ITEM_PRICE_BY_ID)) {
                        preparedStatement.setInt(1, purchasesItem.getId_item());
                        ResultSet rs = preparedStatement.executeQuery();
                        if (rs.next()) {
                            totalCost.updateAndGet(v -> {
                                try {
                                    return v + rs.getDouble("price") * purchasesItem.getAmount();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                    } catch (SQLException e) {
                        Logger.getLogger(DaoItems.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
        );
        return totalCost.get();
    }

    public double checkClientBalance(int clientID) {
        double balance = 0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_CLIENT_BALANCE)) {
            preparedStatement.setInt(1, clientID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoPurchases.class.getName()).log(Level.SEVERE, null, ex);
        }
        return balance;
    }

    public String mostPurchasedItem() {
        List<MostPurchasedItem> items = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_MOST_PURCHASED_PRODUCT);
            try {
                while (rs.next()) {
                    MostPurchasedItem item = new MostPurchasedItem();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getDouble("price"));
                    item.setAmount(rs.getInt("purchases_items.amount"));
                    items.add(item);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoPurchases.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoPurchases.class.getName()).log(Level.SEVERE, null, e);
        }
        AtomicInteger contador = new AtomicInteger();
        List<Integer> amounts = new ArrayList<>();
        for (int i = 1; i < items.size(); i++) {
            int finalI = i;
            items.stream().filter(mostPurchasedItem -> mostPurchasedItem.getId() == finalI).forEach(
                    mostPurchasedItem -> contador.addAndGet(mostPurchasedItem.getAmount())
            );
            amounts.add(i - 1, contador.get());
            contador.set(0);
        }
        int maxAmount = amounts.stream().max(Comparator.comparingInt(Integer::intValue)).get();
        for (int i = 0; i < amounts.size(); i++) {
            if (amounts.get(i) == maxAmount) {
                int finalI = i;
                return items.stream().filter(mostPurchasedItem -> mostPurchasedItem.getId() == finalI)
                        .findFirst().get().getName() + " has been bought " + maxAmount + " times";
            }
        }
        return null;
    }
}