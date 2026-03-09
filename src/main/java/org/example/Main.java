package org.example;

import org.example.data.DiscountDao;
import org.example.model.DiscountTypes;
import org.example.services.CheckoutService;
import org.example.services.DiscountService;
import org.flywaydb.core.Flyway;

import java.io.InputStream;
import java.math.BigDecimal;
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
            flyway.repair();
            flyway.migrate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        CheckoutService checkoutService = new CheckoutService();
        Printer printer = new Printer();
        DiscountService discountService = new DiscountService();

        discountService.addDiscount("butter", new BigDecimal(3), DiscountTypes.percentage, 0.2);
        discountService.addDiscount("tomato", new BigDecimal(5), DiscountTypes.gratis, 1);

        ArrayList<String> cart = new ArrayList<>(Arrays.asList(
                "butter", "butter", "butter", "butter", "butter",
                "water", "water", "water",
                "tomato", "tomato",
                "bread", "bread", "bread",
                "chocolate bar"
        ));

        checkoutService.addNewDiscount(new BreadDiscount());
        Receipt receipt = checkoutService.checkout(cart);
        System.out.println(String.format("I'm gonna pay: %2f", receipt.totalPrice));
        printer.printAReceipt(receipt);
    }
}