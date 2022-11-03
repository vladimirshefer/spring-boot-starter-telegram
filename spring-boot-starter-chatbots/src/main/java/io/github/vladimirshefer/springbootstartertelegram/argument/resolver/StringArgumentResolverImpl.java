package io.github.vladimirshefer.springbootstartertelegram.argument.resolver;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;


/**
 * Sets message text to argument if it is of type String without any annotations.
 *
 * Filters out controller method if update has no text and method has String parameter without
 * Nullable annotation.
 */
@Component
public class StringArgumentResolverImpl extends FilteringArgumentResolver {

  @Override
  protected Object resolve(HandlerArgumentDefinition argument, Update update) {
    if (argument.getType().equals(String.class) && argument.getAnnotations().isEmpty()) {
      return UpdateUtil.getMessageTextOrNull(update);
    }

    return null;
  }

  @Override
  public boolean updateHasValue(Update update) {
    return Optional.ofNullable(update)
      .map(Update::getMessage)
      .map(Message::getText)
      .isPresent();
  }

  @Override
  public boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return !argument.getAnnotations().isEmpty() &&
      argument.getType().equals(String.class) &&
      !argument.hasAnnotation("Nullable");
  }

}
