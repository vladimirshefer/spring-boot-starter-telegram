package io.github.vladimirshefer.spring.chatbots.method_filter;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;

@Component
public class RequestMappingAnnotationPatternMatchesMethodFilterImpl implements MethodFilter {

  @Override
  public boolean isMatch(EventFacade event, HandlerMethodDefinition method) {
    boolean noMapping = method.getRequestMappingValue().equals("");

    return noMapping || textMatches(event, method);
  }

  private boolean textMatches(EventFacade event, HandlerMethodDefinition method) {
    if (event.getMessage() == null || event.getMessage().getMessageText() == null) {
      return false;
    }

    String text = event.getMessage().getMessageText();
    return text.matches(method.getRequestMappingValue());
  }

}
