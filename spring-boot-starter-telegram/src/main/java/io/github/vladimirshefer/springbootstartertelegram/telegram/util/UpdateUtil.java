package io.github.vladimirshefer.springbootstartertelegram.telegram.util;

import java.util.List;
import java.util.Optional;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import javax.annotation.Nullable;

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

  private static Optional<String> getMessageTextOptional(Update update) {
    return getMessageOptional(update)
        .map(Message::getText);
  }

  @Nullable
  public static String getMessageTextOrNull(Update update) {
    return getMessageTextOptional(update)
        .orElse(null);
  }

  private static Optional<Poll> getPollOptional(Update update){
    return getMessageOptional(update)
      .map(Message::getPoll);
  }

  @Nullable
  public static Poll getPollOrNull(Update update){
    return getPollOptional(update)
      .orElse(null);
  }

  public static List<PhotoSize> getPhotoOrNull(Update update) {
    return getPhotoOptional(update).orElse(null);
  }

  private static Optional<List<PhotoSize>> getPhotoOptional(Update update) {
    return getMessageOptional(update).map(Message::getPhoto);
  }
}
