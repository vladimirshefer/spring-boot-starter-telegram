package com.github.vladimirshefer.springbootstartertelegram.method_filter;

import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
public class TextBodyMethodFilterImpl implements MethodFilter {

  @Override
  public boolean isMatch(Update update, MappingDefinition method) {
    return !Arrays
            .asList(method.getTargetMethod().getParameterTypes())
            .contains(String.class) || update.getMessage().getText() == null;
  }
}
