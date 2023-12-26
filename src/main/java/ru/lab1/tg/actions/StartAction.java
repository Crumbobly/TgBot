package ru.lab1.tg.actions;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lab1.tg.bot.BotResponse;

public class StartAction implements Action {

    private final String out = """
            Привет - вы в нашем продуктовом магазине.
            Здесь вы можете заказать самые свежие фрукты в России.
            Для использования бота необходимо зарегистрироваться.
            Для просмотра всех команд введи /help
            """;

    @Override
    public BotResponse handle(Update update) {
        var msg = update.getMessage();
        var chatId = msg.getChatId().toString();
        return new BotResponse(null, new SendMessage(chatId, out));
    }

    @Override
    public BotResponse callback(Update update) {
        return handle(update);
    }
}
