package io.github.vladimirshefer.spring.chatbots.core.api;

import io.github.vladimirshefer.spring.chatbots.core.engine.ChatBotInitializer;
import io.github.vladimirshefer.spring.chatbots.core.engine.EventListener;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * If the implementation of
 * ChatBot is a {@link org.springframework.stereotype.Component},
 * then you should not manually invoke init method,
 * it will be automatically invoked by
 * {@link ChatBotInitializer}
 * unless autoInit method returns false.
 */
public interface ChatBot {

  /**
   * Name of the bot.
   * Empty value is not recommended. If bot has no name, then use any string (e.g. "my_bot")
   */
  @Nonnull
  default String getName() {
    return UUID.randomUUID().toString();
  }

  /**
   * Messenger name (e.g. "telegram", "discord", "slack", "twitch", etc.)
   */
  @Nonnull
  String getType();

  default boolean autoInit() {
    return true;
  }

  /**
   * This method should run the bot and delegate all messages to the eventListener
   * @param eventListener the
   */
  void init(EventListener eventListener);

}
