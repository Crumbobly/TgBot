package ru.lab1.tg.actions;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lab1.tg.bot.BotResponse;
import ru.lab1.tg.domain.ListedProducts;

public class ShowProductsAction implements Action{

    private final ListedProducts productList;

    public ShowProductsAction(ListedProducts products) {
        this.productList = products;
    }

    @Override
    public BotResponse handle(Update update) {
        Message msg = update.getMessage();
        String chatId = msg.getChatId().toString();
        return new BotResponse(null, new SendMessage(chatId, productList.toString()));
    }

    @Override
    public BotResponse callback(Update update) {
        return null;
    }
}
