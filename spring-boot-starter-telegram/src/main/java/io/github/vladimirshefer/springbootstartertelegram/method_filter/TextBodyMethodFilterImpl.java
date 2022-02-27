package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

/**
 * Filters out controller method if update has no text and method has String parameter without
 * Nullable annotation.
 */
@Component
public class TextBodyMethodFilterImpl extends SimpleMethodFilter {

  @Override
  public boolean updateHasValue(Update update) {
    return Optional.ofNullable(update).map(Update::getMessage).map(Message::getText).isPresent();
  }

  @Override
  public boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return !argument.getAnnotations().isEmpty() &&
      argument.getType().equals(String.class) &&
      !argument.hasAnnotation("Nullable");
  }

}
