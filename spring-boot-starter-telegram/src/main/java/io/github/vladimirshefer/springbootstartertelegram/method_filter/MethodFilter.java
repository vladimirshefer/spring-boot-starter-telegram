package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MethodFilter {

  boolean isMatch(Update update, MappingDefinition method);

}
