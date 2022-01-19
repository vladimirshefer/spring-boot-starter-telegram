package com.github.vladimirshefer.springbootstartertelegram.telegram.util;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateUtil {

  private static Optional<Message> getMessageOptional(Update update) {
    return Optional.ofNullable(update)
        .map(Update::getMessage);
  }

  private static Optional<String> getMessageTextOptional(Update update) {
    return getMessageOptional(update)
        .map(Message::getText);
  }

  public static String getMessageTextOrNull(Update update) {
    return getMessageTextOptional(update)
        .orElse(null);
  }

}
