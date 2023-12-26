package ru.lab1.tg.actions;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lab1.tg.bot.BotResponse;

public interface Action {
    BotResponse handle(Update update);

    BotResponse callback(Update update);
}
