package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.telegram.util.UpdateUtil;
import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.ChatId;
import io.github.vladimirshefer.spring.chatbots.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class ChatIdAnnotationArgumentResolverImpl extends FilteringArgumentResolver {

  @Override
  public Object resolve(HandlerArgumentDefinition argument, Update update) {
    if (argument.hasAnnotation(ChatId.class)) {
      return UpdateUtil.getChatId(update);
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
