package io.github.vladimirshefer.springbootstartertelegram.argument.resolver;

import io.github.vladimirshefer.springchat.core.messaging.annotations.ChatId;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getChatId;

@Component
public class ChatIdAnnotationArgumentResolverImpl extends FilteringArgumentResolver {

  @Override
  protected Object resolve(HandlerArgumentDefinition argument, Update update) {
    if (argument.hasAnnotation(ChatId.class)) {
      return getChatId(update);
    }

    return null;
  }

  @Override
  protected boolean updateHasValue(Update update) {
    return Optional.ofNullable(update)
      .map(Update::getMessage)
      .map(Message::getChatId)
      .isPresent();
  }

  @Override
  protected boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(ChatId.class) || !argument.hasAnnotation("Nullable");
  }

}
