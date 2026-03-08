package org.example;

import org.example.services.CheckoutService;
import org.example.services.Printer;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try {
            Properties prop = new Properties();
            InputStream input = Main.class.getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("Unable to find db.properties in resources");
            }
            prop.load(input);

            //Flyway
            Flyway flyway = Flyway.configure()
                    .dataSource("jdbc:postgresql://localhost:5433/postgres",
                            "postgres",
                            prop.getProperty("hibernate.connection.password"))
                    .load();
            flyway.migrate();
        } catch (Exception e) {
            System.out.println("flyway migration failed. Check db.properties if added");
        }

        CheckoutService checkoutService = new CheckoutService();


        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "butter", "butter", "butter", "butter", "butter",
                "water", "water", "water",
                "tomato", "tomato",
                "bread", "bread", "bread",
                "chocolate bar"
        ));

        checkoutService.addNewDiscount(new BreadDiscount());
        System.out.println(String.format("I'm gonna pay: %2f", checkoutService.toPay(cart)));
        Printer.printAReceipt(cart);
    }
}