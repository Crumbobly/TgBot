package ru.lab1.tg.actions;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lab1.tg.bot.BotResponse;
import ru.lab1.tg.domain.ListedProducts;
import ru.lab1.tg.domain.QRCodeHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BuyAction implements Action{
    private final ListedProducts basket;

    public BuyAction(ListedProducts basket) {
        this.basket = basket;
    }

    @Override
    public BotResponse handle(Update update) {
        Message msg = update.getMessage();
        String chatId = msg.getChatId().toString();
        Double sum = basket.getSum();
        String out = "Стоимость корзины - " + sum.toString() + " рублей. Чтобы оформить заказ - введите 'Оформить'";
        return new BotResponse(null, new SendMessage(chatId, out));
    }

    @Override
    public BotResponse callback(Update update) {
        Message msg = update.getMessage();
        String chatId = msg.getChatId().toString();
        String text = msg.getText();
        String out;
        byte[] image = null;

        if (text.equals("Оформить")){
            out = "Заказ сгенерирован.\nКорзина очищена.\nДля оплаты отсканируйте QR-код.";
            image = QRCodeHandler.generateQRCodeBytes(
                    "ST00012|" +
                            "Name=КРЫМОВ ВАЛЕРИЙ ПАВЛОВИЧ|" +
                            "PersonalAcc=40817810277030744943|" +
                            "BankName=КАЛУЖСКОЕ ОТДЕЛЕНИЕ N8608 ПАО СБЕРБАНК|" +
                            "BIC=042908612|" +
                            "CorrespAcc=30101810100000000612|" +
                            "Sum=" + basket.getSum() * 100 + "|" +
                            "Purpose=Возврат подотчетных средств"
            );
            basket.clear();
        }

        else {
            out = "Оформление заказа отменено.";
        }

        if (image == null){
            return new BotResponse();
        }

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
            SendPhoto sendPhoto = new SendPhoto(chatId, new InputFile(inputStream, "QRCode"));
            inputStream.close();
            return new BotResponse(sendPhoto, new SendMessage(chatId, out));
        }
        catch (IOException e){
            return new BotResponse();
        }

    }

}
