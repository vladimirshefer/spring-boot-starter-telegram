package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.annotations.ForwardedFrom;
import io.github.vladimirshefer.spring.chatbots.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Component
public class ForwardedFromAnnotationFilteringArgumentResolverImpl extends FilteringArgumentResolver {

  public Object resolve(
    HandlerArgumentDefinition argument, Update update
  ) {

    if (!argument.hasAnnotation(ForwardedFrom.class)) {
      return null;
    }

    Class<?> parameterType = argument.getType();

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

  @Override
  public boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(ForwardedFrom.class) &&
      !argument.hasAnnotation("Nullable");
  }

  @Override
  public boolean updateHasValue(Update update) {
    return Optional.ofNullable(update)
      .map(Update::getMessage)
      .map(Message::getForwardFrom)
      .isPresent();
  }

}
