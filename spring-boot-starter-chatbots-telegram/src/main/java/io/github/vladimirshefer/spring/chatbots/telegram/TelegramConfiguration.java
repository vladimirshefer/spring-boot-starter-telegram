package io.github.vladimirshefer.spring.chatbots.telegram;

import io.github.vladimirshefer.spring.chatbots.core.SpringChatbotsCoreConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ComponentScan
@Slf4j
@AutoConfigureAfter(SpringChatbotsCoreConfiguration.class)
public class TelegramConfiguration {

  /**
   * This method is a main method to initialize telegram bot and start handling telegram messages.
   */
  @SneakyThrows
  @Bean
  public TelegramBotsApi getTelegramBotsApi() {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    return telegramBotsApi;
  }


}
