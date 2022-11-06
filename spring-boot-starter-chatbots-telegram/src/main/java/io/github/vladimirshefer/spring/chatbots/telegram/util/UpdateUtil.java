package io.github.vladimirshefer.spring.chatbots.telegram.util;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class UpdateUtil {

  private static Optional<String> getChatIdOptional(Update update) {
    return getMessageOptional(update)
        .map(Message::getChatId)
        .map(Object::toString);
  }

  private static String getChatIdOrNull(Update update) {
    return getChatIdOptional(update)
        .orElse(null);
  }

  public static String getChatId(Update update) {
    String chatIdOrNull = getChatIdOrNull(update);

    if (chatIdOrNull == null) {
      throw new IllegalStateException("Could not find chat id for update");
    }

    return chatIdOrNull;
  }

  private static Optional<Message> getMessageOptional(Update update) {
    return Optional.ofNullable(update)
        .map(Update::getMessage);
  }

}
