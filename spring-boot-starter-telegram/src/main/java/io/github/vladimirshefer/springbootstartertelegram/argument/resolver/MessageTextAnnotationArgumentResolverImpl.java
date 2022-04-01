package io.github.vladimirshefer.springbootstartertelegram.argument.resolver;

import io.github.vladimirshefer.springbootstartertelegram.annotations.MessageText;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getMessageTextOrNull;

@Component
public class MessageTextAnnotationArgumentResolverImpl extends FilteringArgumentResolver {

  @Override
  protected Object resolve(HandlerArgumentDefinition argument, Update update) {
    if (argument.hasAnnotation(MessageText.class)) {
      return getMessageTextOrNull(update);
    }

    return null;
  }

  @Override
  protected boolean updateHasValue(Update update) {
    return Optional.ofNullable(update)
      .map(Update::getMessage)
      .map(Message::getText)
      .isPresent();
  }

  @Override
  protected boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(MessageText.class) && !argument.hasAnnotation("Nullable");
  }
}
