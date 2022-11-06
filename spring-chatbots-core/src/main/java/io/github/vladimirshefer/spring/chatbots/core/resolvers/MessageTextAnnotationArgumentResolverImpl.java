package io.github.vladimirshefer.spring.chatbots.core.resolvers;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.MessageText;
import io.github.vladimirshefer.spring.chatbots.core.engine.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageTextAnnotationArgumentResolverImpl extends FilteringArgumentResolver {

  @Override
  public boolean shouldResolve(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(MessageText.class);
  }

  @Override
  public Object resolve(HandlerArgumentDefinition argument, EventFacade event) {
    return Optional.ofNullable(event.getMessage())
      .map(MessageFacade::getMessageText)
      .orElse(null);
  }

  @Override
  protected boolean hasValue(EventFacade event) {
    return Optional.ofNullable(event)
      .map(EventFacade::getMessage)
      .map(MessageFacade::getMessageText)
      .isPresent();
  }

  @Override
  protected boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(MessageText.class) && !argument.hasAnnotation("Nullable");
  }

}
