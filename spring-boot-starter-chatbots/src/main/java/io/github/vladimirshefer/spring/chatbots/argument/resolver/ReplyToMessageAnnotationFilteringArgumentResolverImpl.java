package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.annotations.ReplyToMessage;
import io.github.vladimirshefer.spring.chatbots.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Component
public class ReplyToMessageAnnotationFilteringArgumentResolverImpl extends FilteringArgumentResolver {

  @Override
  public Object resolve(
    HandlerArgumentDefinition argument, Update update
  ) {

    if (!argument.hasAnnotation(ReplyToMessage.class)) {
      return null;
    }

    Class<?> parameterType = argument.getType();

    Message replyToMessage = Optional.ofNullable(update)
      .map(Update::getMessage)
      .map(Message::getReplyToMessage)
      .orElse(null);

    if (replyToMessage == null) {
      return null;
    }

    if (parameterType.equals(String.class)) {
      return replyToMessage.getText();
    }

    if (parameterType.equals(User.class)) {
      return replyToMessage.getFrom();
    }

    return null;
  }


  @Override
  public boolean updateHasValue(Update update) {
    return Optional.ofNullable(update)
      .map(Update::getMessage)
      .map(Message::getReplyToMessage)
      .isPresent();
  }

  public boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(ReplyToMessage.class) && !argument.hasAnnotation("Nullable");
  }

}
