package io.github.vladimirshefer.spring.chatbots;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.handler.EventListener;

import java.util.List;
import java.util.stream.Collectors;

import io.github.vladimirshefer.spring.chatbots.telegram.facade.TelegramEventFacade;
import io.github.vladimirshefer.spring.chatbots.telegram.util.UpdateUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    @Value("${spring.chatbots.telegram.bot.token}") String botToken,
    @Value("${spring.chatbots.telegram.bot.name}") String botName,
    EventListener updateHandler
  ) {
    return new TelegramLongPollingBot() {
      @SneakyThrows
      @Override
      public void onUpdateReceived(Update update) {
        final TelegramEventFacade telegramEventFacade = new TelegramEventFacade(update);
        List<Object> result = updateHandler.handleMessage(telegramEventFacade);
        List<BotApiMethod<?>> answers = result
          .stream()
          .map(it -> resultToApiMethod(update, it))
          .collect(Collectors.toList());

        for (BotApiMethod<?> answer : answers) {
          if (answer != null) {
            this.sendApiMethod(answer);
          }
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
    };
  }

  private static BotApiMethod<?> resultToApiMethod(Update update, Object methodInvocationResult) {
    if (methodInvocationResult instanceof BotApiMethod) {
      return (BotApiMethod<?>) methodInvocationResult;
    }

    return sendSimpleMessage(update, methodInvocationResult.toString());
  }

  private static SendMessage sendSimpleMessage(Update update, String result) {
    SendMessage sendMessage = new SendMessage(UpdateUtil.getChatId(update), result);
    if (update.getMessage() != null && update.getMessage().getMessageId() != null) {
      sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
    }
    return sendMessage;
  }

}
