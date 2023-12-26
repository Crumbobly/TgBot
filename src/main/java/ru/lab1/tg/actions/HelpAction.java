package ru.lab1.tg.actions;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lab1.tg.bot.BotResponse;

public class HelpAction implements Action{

    private final String out = """
            Список команд:
            /show_products - просмотр списка товаров
            /show_basket - просмотр корзины
            /add - добавление нового товара, id - номер товара
            /remove - удаление товара, id - номер товара
            /clear - очистка корзины
            /buy - оформление покупки
            """;

    @Override
    public BotResponse handle(Update update) {
        var msg = update.getMessage();
        var chatId = msg.getChatId().toString();
        return new BotResponse(null, new SendMessage(chatId, out));
    }

    @Override
    public BotResponse callback(Update update) {
        return null;
    }

}
