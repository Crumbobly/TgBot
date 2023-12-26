package ru.lab1.tg.actions;

import org.apache.commons.lang3.tuple.Pair;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lab1.tg.bot.BotResponse;
import ru.lab1.tg.domain.ListedProducts;
import ru.lab1.tg.domain.Product;

import static java.util.Collections.max;

public class ClearBasketAction implements Action{

    private final ListedProducts productList;
    private final ListedProducts basket;

    public ClearBasketAction(ListedProducts products, ListedProducts basket) {
        this.productList = products;
        this.basket = basket;
    }

    @Override
    public BotResponse handle(Update update) {

        Message msg = update.getMessage();
        String chatId = msg.getChatId().toString();
        String out = "Корзина очищена";

        var lastKey = max(basket.getProducts().keySet());

        for (int id = lastKey; id >= 0; id--) {
            var pc = basket.getProductAndCountById(id);
            int maxCount = pc.right;
            for (int count = 0; count < maxCount; count++) {
                productList.addProduct(basket.popProduct(id));
            }
        }

        return new BotResponse(null , new SendMessage(chatId, out));
    }

    @Override
    public BotResponse callback(Update update) {
        return null;
    }

}
