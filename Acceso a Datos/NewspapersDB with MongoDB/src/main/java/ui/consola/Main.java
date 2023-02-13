package ui.consola;

import data.DaoQueries;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer container = SeContainerInitializer.newInstance();
        container.initialize();
        DaoQueries daoQueries = container.initialize().select(DaoQueries.class).get();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("a. Get the id of the readers of articles of a specific type");
            System.out.println("b. Get the average rating of the articles read by a reader, group by newspaper");
            System.out.println("c. Get the type of articles that are most read");
            System.out.println("d. Show a list with the number of articles of each type of the selected newspaper");
            System.out.println("e. Get the description and the number of readers of each article");
            System.out.println("f. Get the name of the 100 oldest subscriptors of newspaper Tempo");
            System.out.println("g. Get the articles of a given type, together with the name of the newspaper");
            System.out.println("h. Get the number of Sports articles by newspaper");
            System.out.println("i. Get the name of the newspaper with highest number of Sports articles");
            System.out.println("j. Get the articles with no rating lower than 3");
            System.out.println("k. Get the average number of subscriptions per reader");
            System.out.println("l. Number of read articles per reader");
            System.out.println("m. Number of articles with average rating greater than 4");
            System.out.println("n. Readers with no review lower than 3");
            System.out.println("o. Get the articles with a rating lower than 5 of a given newspaper, indicating if the reader has rated more than 4 articles with a lower-than-5 rating");
            System.out.println("p. Readers that are not registered as users (Use $lookup)");
            System.out.println("q. Quit");

            String option = sc.nextLine();

            switch (option) {
                case "a" -> {
                    System.out.println("Query 1:");
                    daoQueries.query1().forEach(System.out::println);
                }
                case "b" -> {
                    System.out.println("Query 2:");
                    daoQueries.query2().forEach(System.out::println);
                }
                case "c" -> {
                    System.out.println("Query 3:");
                    daoQueries.query3().forEach(System.out::println);
                }
                case "d" -> {
                    System.out.println("Query 4:");
                    daoQueries.query4().forEach(System.out::println);
                }
                case "e" -> {
                    System.out.println("Query 5:");
                    daoQueries.query5().forEach(System.out::println);
                }
                case "f" -> {
                    System.out.println("Query 6:");
                    daoQueries.query6().forEach(System.out::println);
                }
                case "g" -> {
                    System.out.println("Query 7:");
                    daoQueries.query7().forEach(System.out::println);
                }
                case "h" -> {
                    System.out.println("Query 8:");
                    daoQueries.query8().forEach(System.out::println);
                }
                case "i" -> {
                    System.out.println("Query 9:");
                    daoQueries.query9().forEach(System.out::println);
                }
                case "j" -> {
                    System.out.println("Query 10:");
                    daoQueries.query10().forEach(System.out::println);
                }
                case "k" -> {
                    System.out.println("Query 11:");
                    daoQueries.query11().forEach(System.out::println);
                }
                case "l" -> {
                    System.out.println("Query 12:");
                    daoQueries.query12().forEach(System.out::println);
                }
                case "m" -> {
                    System.out.println("Query 13:");
                    daoQueries.query13().forEach(System.out::println);
                }
                case "n" -> {
                    System.out.println("Query 14:");
                    daoQueries.query14().forEach(System.out::println);
                }
                case "o" -> {
                    System.out.println("Query 15:");
                    daoQueries.query15().forEach(System.out::println);
                }
                case "p" -> {
                    System.out.println("Query 16:");
                    daoQueries.query16().forEach(System.out::println);
                }
                case "q" -> {
                    System.out.println("Bye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option");
            }
        }
    }
}