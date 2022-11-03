package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.telegram.util.UpdateUtil;
import io.github.vladimirshefer.spring.chatbots.annotations.MessageText;
import io.github.vladimirshefer.spring.chatbots.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class MessageTextAnnotationArgumentResolverImpl extends FilteringArgumentResolver {

  @Override
  protected Object resolve(HandlerArgumentDefinition argument, Update update) {
    if (argument.hasAnnotation(MessageText.class)) {
      return UpdateUtil.getMessageTextOrNull(update);
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
