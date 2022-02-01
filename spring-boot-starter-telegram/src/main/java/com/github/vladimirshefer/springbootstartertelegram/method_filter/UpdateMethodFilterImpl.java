package com.github.vladimirshefer.springbootstartertelegram.method_filter;

import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UpdateMethodFilterImpl implements MethodFilter {
  @Override
  public boolean isMatch(Update update, MappingDefinition method) {
    return Arrays
      .stream(method.getTargetMethod().getParameterTypes())
      .collect(Collectors.toList())
      .contains(Update.class);
  }
}
