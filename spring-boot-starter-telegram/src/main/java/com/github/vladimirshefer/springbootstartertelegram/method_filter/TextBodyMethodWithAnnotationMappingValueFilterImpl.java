package com.github.vladimirshefer.springbootstartertelegram.method_filter;

import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
public class TextBodyMethodWithAnnotationMappingValueFilterImpl implements MethodFilter {

  @Override
  public boolean isMatch(Update update, MappingDefinition method) {
    return method.getRequestMappingValue().equals("") || update.getMessage().getText() != null && update.getMessage().getText().matches(method.getRequestMappingValue());
  }
}
