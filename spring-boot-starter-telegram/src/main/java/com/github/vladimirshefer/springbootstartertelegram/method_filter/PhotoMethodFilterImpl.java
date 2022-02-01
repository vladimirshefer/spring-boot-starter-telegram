package com.github.vladimirshefer.springbootstartertelegram.method_filter;

import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class PhotoMethodFilterImpl implements MethodFilter {
  @Override
  public boolean isMatch(Update update, MappingDefinition method) {
    return update.getMessage().getPhoto() != null &&
      Arrays
        .stream(method.getTargetMethod().getGenericParameterTypes())
        .anyMatch(p -> p.getTypeName().contains("PhotoSize"));
  }
}
