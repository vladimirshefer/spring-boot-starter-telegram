package io.github.vladimirshefer.spring.chatbots.core.resolvers.impl;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.ChatId;
import io.github.vladimirshefer.spring.chatbots.core.engine.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.core.resolvers.FilteringArgumentResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChatIdAnnotationArgumentResolverImpl extends FilteringArgumentResolver {

  @Override
  public boolean shouldResolve(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(ChatId.class);
  }

  @Override
  public Object resolve(HandlerArgumentDefinition argument, EventFacade event) {
    return Optional.ofNullable(event.getMessage())
      .map(MessageFacade::getChatId)
      .orElse(null);
  }

  @Override
  protected boolean hasValue(EventFacade update) {
    return Optional.ofNullable(update)
      .map(EventFacade::getMessage)
      .map(MessageFacade::getChatId)
      .isPresent();
  }

  @Override
  protected boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(ChatId.class) || !argument.hasAnnotation("Nullable");
  }

}
