package dao;

import config.ConfigXML;
import dao.common.Constantes;
import dao.common.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import model.Client;
import model.Clients;
import model.Purchase;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoClients {
    private final DBConnection db;
    private final DaoPurchases dao;

    @Inject
    public DaoClients(DBConnection db, DaoPurchases dao) {
        this.db = db;
        this.dao = dao;
    }

    public Either<String, Boolean> save(List<Client> clientList) {
        JAXBContext context;
        Marshaller marshaller;
        try {
            context = JAXBContext.newInstance(Clients.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            return Either.left(e.getMessage());
        }

        List<Purchase> purchases = dao.getAll().get();
        clientList.forEach(client -> purchases.forEach(purchase -> {
            if (purchase.getId_client() == client.getId()) {
                if (client.getPurchases() == null) {
                    client.setPurchases(new ArrayList<>());
                }
                List<Purchase> clientPurchases = client.getPurchases();
                clientPurchases.add(purchase);
                client.setPurchases(clientPurchases);
            }
        }));

        Clients clients = new Clients();
        clients.setClient(clientList);
        Path xmlFile = Paths
                .get(ConfigXML.getInstance().getProperty(Constantes.XML_PATH));
        try {
            marshaller.marshal(clients, Files.newOutputStream(xmlFile));
        } catch (JAXBException | IOException e) {
            return Either.left(e.getMessage());
        }
        return Either.right(true);
    }

    public Either<Integer, List<Client>> getAll() {
        List<Client> clients = new ArrayList<>();
        try {
            String query = SQLQueries.SELECT_CLIENTS;
            JdbcTemplate jdbc = new JdbcTemplate(db.getHikariDataSource());
            clients = jdbc.query(query, BeanPropertyRowMapper.newInstance(Client.class));
        } catch (DataAccessException e) {
            Logger.getLogger(DaoClients.class.getName()).log(Level.SEVERE, null, e);
        }
        return clients.isEmpty() ? Either.left(-1) : Either.right(clients);
    }
}