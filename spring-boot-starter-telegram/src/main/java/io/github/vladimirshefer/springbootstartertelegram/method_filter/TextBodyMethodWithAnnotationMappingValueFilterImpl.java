package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TextBodyMethodWithAnnotationMappingValueFilterImpl implements MethodFilter {

  @Override
  public boolean isMatch(Update update, MappingDefinition method) {
    return method.getRequestMappingValue().equals("") || update.getMessage().getText() != null && update.getMessage().getText().matches(method.getRequestMappingValue());
  }
}
