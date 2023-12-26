package ru.lab1.tg.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class BotResponse {
    private final SendPhoto sendPhoto;
    private final SendMessage sendMessage;

    public BotResponse(){
        this.sendPhoto = null;
        this.sendMessage = null;
    }
    public BotResponse(SendPhoto sendPhoto, SendMessage sendMessage) {
        this.sendPhoto = sendPhoto;
        this.sendMessage = sendMessage;
    }

    public SendPhoto getSendPhoto() {
        return sendPhoto;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }
}