package io.github.vladimirshefer.spring.chatbots.core.resolvers.impl;

import io.github.vladimirshefer.spring.chatbots.core.engine.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.facade.MessageFacade;
import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.File;
import io.github.vladimirshefer.spring.chatbots.core.resolvers.FilteringArgumentResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FileAnnotationArgumentResolver extends FilteringArgumentResolver {

  @Override
  public boolean shouldResolve(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(File.class) && argument.getType().equals(List.class);
  }

  @Override
  public Object resolve(HandlerArgumentDefinition argument, EventFacade event) {
    return Optional.ofNullable(event.getMessage())
      .map(MessageFacade::getAttachments)
      .orElse(null);
  }

  @Override
  protected boolean hasValue(EventFacade event) {
    return Optional.ofNullable(event)
      .map(EventFacade::getMessage)
      .map(MessageFacade::getAttachments)
      .isPresent();
  }

  @Override
  protected boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.hasAnnotation(File.class) && !argument.hasAnnotation("Nullable");
  }
}
