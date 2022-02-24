package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RequestMappingAnnotationPatternMatchesMethodFilterImpl implements MethodFilter {

  @Override
  public boolean isMatch(Update update, HandlerMethodDefinition method) {
    boolean noMapping = method.getRequestMappingValue().equals("");

    return noMapping || textMatchess(update, method);
  }

  private boolean textMatchess(Update update, HandlerMethodDefinition method) {
    String text = update.getMessage().getText();
    return text != null && text.matches(method.getRequestMappingValue());
  }

}
