package ui.consola;

import data.DaoQueries;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer container = SeContainerInitializer.newInstance();
        container.initialize();
        DaoQueries daoQueries = container.initialize().select(DaoQueries.class).get();
//        System.out.println("Query 1:");
//        daoQueries.query1().forEach(System.out::println);
//        System.out.println("Query 2:");
//        daoQueries.query2().forEach(System.out::println);
//        System.out.println("Query 3:");
//        daoQueries.query3().forEach(System.out::println);
//        System.out.println("Query 4:");
//        daoQueries.query4().forEach(System.out::println);
//        System.out.println("Query 5:");
//        daoQueries.query5().forEach(System.out::println);
//        System.out.println("Query 6:");
//        daoQueries.query6().forEach(System.out::println);
//        System.out.println("Query 7:");
//        daoQueries.query7().forEach(System.out::println);
//        System.out.println("Query 8:");
//        daoQueries.query8().forEach(System.out::println);
//        System.out.println("Query 9:");
//        daoQueries.query9().forEach(System.out::println);
//        System.out.println("Query 10:");
//        daoQueries.query10().forEach(System.out::println);
//        System.out.println("Query 11:");
//        daoQueries.query11().forEach(System.out::println);
//        System.out.println("Query 12:");
//        daoQueries.query12().forEach(System.out::println);
//        System.out.println("Query 13:");
//        daoQueries.query13().forEach(System.out::println);
        System.out.println("Query 14:");
        daoQueries.query14().forEach(System.out::println);
//        System.out.println("Query 15:");
//        daoQueries.query15().forEach(System.out::println);
//        System.out.println("Query 16:");
//        daoQueries.query16().forEach(System.out::println);
    }
}