package ui;

import data.DaoReadersSQL;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import modelo.Reader;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        DaoReadersSQL daoReadersSQL = container.select(DaoReadersSQL.class).get();

        daoReadersSQL.getAllReaders().forEach(System.out::println);

        daoReadersSQL.deleteReader(2);

        Reader reader = new Reader(1, "Pepe", LocalDate.of(1990, 1, 1));
        daoReadersSQL.updateReader(reader);
    }
}
