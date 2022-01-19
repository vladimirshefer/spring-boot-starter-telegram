package com.github.vladimirshefer.springbootstartertelegram;

import com.github.vladimirshefer.springbootstartertelegram.handler.UpdateHandler;
import com.github.vladimirshefer.springbootstartertelegram.scan.MappingDefinitionsManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ComponentScan
public class SpringBootStarterTelegramConfiguration {

  @SneakyThrows
  @Bean
  public static TelegramBotsApi getTelegramBotsApi(TelegramLongPollingBot telegramLongPollingBot) {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    telegramBotsApi.registerBot(telegramLongPollingBot);
    return telegramBotsApi;
  }

  @Bean
  public static TelegramLongPollingBot getTelegramLongPollingBot(
      @Value("${spring.telegram.bot.token}") String botToken,
      @Value("${spring.telegram.bot.name}") String botName,
      UpdateHandler updateHandler
  ) {
    return new TelegramLongPollingBot() {
      @Override
      public void onUpdateReceived(Update update) {
        updateHandler.handleMessage(update);
      }

      @Override
      public String getBotUsername() {
        return botName;
      }

      @Override
      public String getBotToken() {
        return botToken;
      }
    };
  }

}