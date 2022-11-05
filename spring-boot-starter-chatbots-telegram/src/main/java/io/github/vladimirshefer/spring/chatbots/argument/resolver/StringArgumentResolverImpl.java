package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.core.messaging.ArgumentResolver;
import io.github.vladimirshefer.spring.chatbots.method_filter.SimpleMethodFilter;
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
public class StringArgumentResolverImpl extends SimpleMethodFilter implements ArgumentResolver {

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

  @Override
  public boolean shouldResolve(HandlerArgumentDefinition argument) {
    return argument.getType().equals(String.class) && argument.getAnnotations().isEmpty();
  }

  @Override
  public Object resolve(HandlerArgumentDefinition argument, EventFacade event) {
      return Optional.of(event)
        .map(EventFacade::getMessage)
        .map(MessageFacade::getMessageText)
        .orElse(null);
  }

}
