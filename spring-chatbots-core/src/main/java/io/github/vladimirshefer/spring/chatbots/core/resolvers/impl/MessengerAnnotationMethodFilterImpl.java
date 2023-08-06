package io.github.vladimirshefer.spring.chatbots.core.resolvers.impl;

import io.github.vladimirshefer.spring.chatbots.core.engine.HandlerMethodDefinition;
import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.Messenger;
import io.github.vladimirshefer.spring.chatbots.core.resolvers.MethodFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MessengerAnnotationMethodFilterImpl implements MethodFilter {
  @Override
  public boolean isMatch(EventFacade event, HandlerMethodDefinition method) {
    Messenger annotation = method.getOriginalMethod().getAnnotation(Messenger.class);
    if (annotation == null) {
      return true;
    }
    return Arrays.asList(annotation.value()).contains(event.getMessengerName());
  }
}
