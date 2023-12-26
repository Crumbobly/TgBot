package ru.lab1.tg.actions;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lab1.tg.bot.BotResponse;
import ru.lab1.tg.domain.ListedProducts;
import ru.lab1.tg.domain.Product;

public class ShiftProductAction implements Action{

    private final ListedProducts productList;
    private final ListedProducts basket;

    public ShiftProductAction(ListedProducts products, ListedProducts basket) {
        this.productList = products;
        this.basket = basket;
    }

    @Override
    public BotResponse handle(Update update) {
        Message msg = update.getMessage();
        String chatId = msg.getChatId().toString();
        String out = "Для перемещения товара введите его id.";
        return new BotResponse(null, new SendMessage(chatId, out));
    }

    @Override
    public BotResponse callback(Update update) {
        Message msg = update.getMessage();
        String chatId = msg.getChatId().toString();
        String text = msg.getText();
        String out;

        try {
            int id = Integer.parseInt(text);
            Product poppedProduct = productList.popProduct(id);
            if (poppedProduct == null){
                out = "Продукта с таким id не существует.";
            }
            else{
                basket.addProduct(poppedProduct);
                out = "Продукт был перемещён.";
            }

        }
        catch (NumberFormatException e){
            out = "Неверный формат.";
        }

        return new BotResponse(null, new SendMessage(chatId, out));
    }
}
