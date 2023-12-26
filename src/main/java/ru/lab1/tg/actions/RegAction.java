package ru.lab1.tg.actions;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lab1.tg.bot.BotResponse;
import ru.lab1.tg.domain.User;

import java.util.Map;


public class RegAction implements Action {

    private final Map<Long, User> registeredUsers;

    private Boolean isValid = false;

    public Boolean getValid() {
        return isValid;
    }

    public RegAction(Map<Long, User> registeredUsers){
        this.registeredUsers = registeredUsers;
    }

    @Override
    public BotResponse handle(Update update) {
        var msg = update.getMessage();
        var chatId = msg.getChatId();
        var out = "Введите имя и почту для регистрации нового пользователя в формате - имя:почта.";
        return new BotResponse(null, new SendMessage(chatId.toString(), out));
    }

    @Override
    public BotResponse callback(Update update) {
        var msg = update.getMessage();
        var chatId = msg.getChatId();
        var text = msg.getText();
        String[] name_email = text.split(":");

        try{
            registeredUsers.put(chatId, new User(name_email[0], name_email[1], chatId));
            isValid = true;
            var out = "Пользователь '" + name_email[0] + "' успешно зарегистрирован. Теперь Вы можете использовать бота.";
            return new BotResponse(null, new SendMessage(chatId.toString(), out));
        }
        catch (IndexOutOfBoundsException e){
            return handle(update);
        }
    }

}
