package com.rudik.SimpleShortURL_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class SimpleGetShortURL extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String userName;
    @Value("${telegram.bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                SendMessage sm = new SendMessage();
                if (!text.isEmpty()) {
                ShortenURL shortenURL = new ShortenURL();
                String shortURL = shortenURL.getShortURL(text);
                if (!shortURL.isEmpty()) {
                    sm.setText(shortURL);
                }
            }else
                {
                    sm.setText("This bot supports text only, please re-send URL link");
                }

            sm.setChatId(String.valueOf(message.getChatId()));
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
}
