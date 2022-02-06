package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Component
public class PhotoMethodFilterImpl implements MethodFilter {
  @Override
  public boolean isMatch(Update update, MappingDefinition method) {
    return Arrays
            .stream(method.getTargetMethod().getGenericParameterTypes())
            .noneMatch(p -> p.getTypeName().contains("PhotoSize")) || update.getMessage().getPhoto() != null;
  }
}
