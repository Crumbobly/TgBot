package ru.lab1.tg.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.lab1.tg.actions.Action;
import ru.lab1.tg.actions.RegAction;
import ru.lab1.tg.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Bot extends TelegramLongPollingBot {

    private final Map<Long, User> registeredUsers = new HashMap<>();
    private final Map<String, String> bindingBy = new ConcurrentHashMap<>();
    private final Map<String, Action> actions;
    private final String botName;

    public Bot(Map<String, Action> actions, String botName, String token) {
        super(token);
        this.actions = actions;
        this.botName = botName;
    }

    public String getBotUsername() {
        return botName;
    }

    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {

            Message message = update.getMessage();
            String key = message.getText();
            Long chatId = message.getChatId();
            String chatIdString = chatId.toString();

            if (!actions.containsKey(key) && bindingBy.isEmpty()){
                var msg = new BotResponse(null, new SendMessage(chatIdString, "Неизвестная команда"));
                send(msg);
                return;
            }

            if (isUserRegistered(chatId) || key.equals("/start")) {

                if (actions.containsKey(key)) {
                    var msg = actions.get(key).handle(update);
                    bindingBy.put(chatIdString, key);
                    send(msg);
                }
                else if (bindingBy.containsKey(chatIdString)) {
                    var msg = actions.get(bindingBy.get(chatIdString)).callback(update);
                    bindingBy.remove(chatIdString);
                    if (msg != null) send(msg);
                }

                if (!key.equals("/start") || isUserRegistered(chatId)) return;
            }
            registration(update, key, chatIdString);

        }
    }

    private void registration(Update update, String key, String chatIdString){

        var regAction = new RegAction(registeredUsers);
        var msg = regAction.handle(update);

        if (actions.containsKey(key)) {
            bindingBy.put(chatIdString, key);
        }
        else if (bindingBy.containsKey(chatIdString)) {
            msg = regAction.callback(update);
            if (regAction.getValid()) bindingBy.remove(chatIdString);
        }
        send(msg);
    }


    private void send(BotResponse botResponse) {

        try {
            if (botResponse.getSendMessage() != null) execute(botResponse.getSendMessage());
            if (botResponse.getSendPhoto() != null) execute(botResponse.getSendPhoto());
        }
        catch (TelegramApiException e) {
            // Добавить логику обработки ошибок
            e.printStackTrace();
        }
    }

    private boolean isUserRegistered(long userId) {
        // Проверяет, зарегистрирован ли пользователь
        return registeredUsers.containsKey(userId);
    }

}
