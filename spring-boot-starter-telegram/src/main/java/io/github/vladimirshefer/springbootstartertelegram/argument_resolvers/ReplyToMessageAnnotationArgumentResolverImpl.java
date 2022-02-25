package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.annotations.ReplyToMessage;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Component
public class ReplyToMessageAnnotationArgumentResolverImpl implements ArgumentResolver {

  @Override
  public Object resolve(
    HandlerMethodDefinition method,
    Update update,
    int index
  ) {
    if (!method.getArgument(index).hasAnnotation(ReplyToMessage.class)) {
      return null;
    }

    Class<?> parameterType = method.getArgument(index).getType();

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

}
