package com.github.vladimirshefer.springbootstartertelegram.method_filter;

import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class TextBodyMethodFilterImpl implements MethodFilter {

  @Override
  public boolean isMatch(Update update, MappingDefinition method) {
    return update.getMessage().getText() != null &&
      Arrays
        .stream(method.getTargetMethod().getParameterTypes())
        .collect(Collectors.toList())
        .contains(String.class);
  }
}