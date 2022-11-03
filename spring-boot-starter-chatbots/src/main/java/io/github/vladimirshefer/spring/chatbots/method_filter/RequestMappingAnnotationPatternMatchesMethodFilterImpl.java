package io.github.vladimirshefer.spring.chatbots.method_filter;

import io.github.vladimirshefer.spring.chatbots.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RequestMappingAnnotationPatternMatchesMethodFilterImpl implements MethodFilter {

  @Override
  public boolean isMatch(Update update, HandlerMethodDefinition method) {
    boolean noMapping = method.getRequestMappingValue().equals("");

    return noMapping || textMatches(update, method);
  }

  private boolean textMatches(Update update, HandlerMethodDefinition method) {
    String text = update.getMessage().getText();
    return text != null && text.matches(method.getRequestMappingValue());
  }

}