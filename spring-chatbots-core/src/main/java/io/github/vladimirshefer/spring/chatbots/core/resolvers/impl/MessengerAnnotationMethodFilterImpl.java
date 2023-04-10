package io.github.vladimirshefer.spring.chatbots.core.resolvers.impl;

import io.github.vladimirshefer.spring.chatbots.core.engine.HandlerMethodDefinition;
import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.Messenger;
import io.github.vladimirshefer.spring.chatbots.core.resolvers.MethodFilter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MessengerAnnotationMethodFilterImpl implements MethodFilter {
  @Override
  public boolean isMatch(EventFacade event, HandlerMethodDefinition method) {
    Messenger annotation = AnnotationUtils.findAnnotation(method.getOriginalMethod(), Messenger.class);
    if (annotation != null && annotation.value().length != 0) {
      if (!Arrays.asList(annotation.value()).contains(event.getMessengerName())) {
        return false;
      }
    }

    Messenger classAnnotation = AnnotationUtils.findAnnotation(method.getOriginalClass(), Messenger.class);
    if (classAnnotation != null && classAnnotation.value().length != 0) {
      //noinspection RedundantIfStatement
      if (!Arrays.asList(classAnnotation.value()).contains(event.getMessengerName())) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean isCompatible(HandlerMethodDefinition method) {
    return true;
  }
}
