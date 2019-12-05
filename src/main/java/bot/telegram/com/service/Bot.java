package bot.telegram.com.service;

import bot.telegram.com.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@ComponentScan
public class Bot extends TelegramLongPollingBot {

    @Autowired
    CityService cityService;

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;


    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates) {
            Message message = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();
            String text;
            City[] cities = new City[0];
            switch (message.getText()) {
                case "/start":
                    text = "Добрый день, " + message.getChat().getFirstName() + "\uD83D\uDE09 Чтобы получить информацию о городе введите его название. " +
                            " На Данный момент я могу предоставить информацию по таким городам\uD83C\uDF06 : ";
                    cities = cityService.findAll().stream().toArray(City[]::new);
                    break;
                case "/city":
                    text = "Доступные города\uD83C\uDF06: ";
                    cities = cityService.findAll().stream().toArray(City[]::new);
                    break;
                default:
                    String cityName = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : message.getText();
                    City city = cityService.findCityByName(cityName);
                    text = city != null ? city.getName() + ". " + city.getDescription() + "\nЧтобы посмотреть другие города введите /city\n" : "Такой город не найден. Чтобы посмотреть список доступных городов введите /city";
            }
            sendMsg(message, text, cities);
        }
    }

    private synchronized void sendMsg(Message message, String text, City... cities) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(message.getChatId().toString())
                .setText(text);
        if (cities.length > 0)
            sendInlineKeyBoardMessage(sendMessage, cities);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void sendInlineKeyBoardMessage(SendMessage message, City... cities) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        Label:
        {
            for (int i = 0; i <= cities.length / 3; i++) {
                List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
                for (int j = i * 3; j < i * 3 + 3; j++) {
                    try {
                        keyboardButtonsRow1.add(new InlineKeyboardButton()
                                .setCallbackData(cities[j].getName())
                                .setText(cities[j].getName()));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        rowList.add(keyboardButtonsRow1);
                        inlineKeyboardMarkup.setKeyboard(rowList);
                        break Label;
                    }
                }
                rowList.add(keyboardButtonsRow1);
            }
        }
        message.setReplyMarkup(inlineKeyboardMarkup);
    }
}
