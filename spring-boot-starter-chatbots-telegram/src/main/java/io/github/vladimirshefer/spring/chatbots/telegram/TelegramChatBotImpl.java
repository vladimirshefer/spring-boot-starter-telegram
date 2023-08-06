package io.github.vladimirshefer.spring.chatbots.telegram;

import io.github.vladimirshefer.spring.chatbots.core.api.ChatBot;
import io.github.vladimirshefer.spring.chatbots.core.engine.EventListener;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import javax.annotation.Nonnull;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramChatBotImpl implements ChatBot {

  @Value("${spring.chatbots.telegram.bot.token}")
  private String botToken;
  @Value("${spring.chatbots.telegram.bot.name}")
  private String botName;

  private final TelegramBotsApi telegramBotsApi;

  @Nonnull
  @Override
  public String getName() {
    return botName;
  }

  @Nonnull
  @Override
  public String getType() {
    return "telegram";
  }

  @Override
  @SneakyThrows
  public void init(EventListener eventListener) {
    TelegramLongPollingBot bot = new TelegramLongPollingBotDelegateImpl(eventListener, botName, botToken);
    log.info("Telegram bot initialized");
    telegramBotsApi.registerBot(bot);
  }

}
