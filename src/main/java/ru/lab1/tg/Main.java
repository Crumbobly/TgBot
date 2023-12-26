package ru.lab1.tg;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.lab1.tg.actions.*;
import ru.lab1.tg.bot.Bot;
import ru.lab1.tg.domain.ListedProducts;
import ru.lab1.tg.domain.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws TelegramApiException, IOException {

        ListedProducts products = initProductList();
        ListedProducts basket = new ListedProducts();
        var tg = new TelegramBotsApi(DefaultBotSession.class);
        var config = new Properties();

        try (var app = Main.class.getClassLoader().getResourceAsStream("app.properties")) {
            config.load(app);
        }

        var actions = Map.of(
                "/start", new StartAction(),
                "/help", new HelpAction(),
                "/show_products", new ShowProductsAction(products),
                "/show_basket", new ShowProductsAction(basket),
                "/add", new ShiftProductAction(products, basket),
                "/remove", new ShiftProductAction(basket, products),
                "/clear", new ClearBasketAction(products, basket),
                "/buy", new BuyAction(basket)

        );

        tg.registerBot(
                new Bot(
                        actions,
                        config.getProperty("tg.username"),
                        config.getProperty("tg.token")
                )
        );

    }

    public static ListedProducts initProductList(){

        Random random = new Random();

        ListedProducts productList = new ListedProducts();

        List<Product> products = List.of(
                new Product("Яблоко", 20.0),
                new Product("Банан", 27.5),
                new Product("Морковь", 55.0),
                new Product("Ананас", 200.0),
                new Product("Клубника", 443.0),
                new Product("Бутылка воды", 99.0)
        );

        for (Product p: products) {
            for (int i = 0; i < random.nextInt(1, 10); i++) {
                productList.addProduct(p);
            }
        }

        return productList;
    }
}
