package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.annotations.ForwardedFrom;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Component
public class ForwardedFromAnnotationArgumentResolverImpl implements ArgumentResolver {

  @Override
  public Object resolve(
    HandlerMethodDefinition method,
    Update update,
    int index
  ) {
    if (!method.getArgument(index).hasAnnotation(ForwardedFrom.class)) {
      return null;
    }

    Class<?> parameterType = method.getArgument(index).getType();

    User forwardedFrom = Optional.ofNullable(update)
      .map(Update::getMessage)
      .map(Message::getForwardFrom)
      .orElse(null);

    if (forwardedFrom == null) {
      return null;
    }

    if (parameterType.equals(User.class)) {
      return forwardedFrom;
    }

    return null;
  }

}
