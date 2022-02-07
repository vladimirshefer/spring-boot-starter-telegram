package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Gives ability to filter out controller methods if they are not appropriate for specific telegram
 * message.
 */
public interface MethodFilter {

  /**
   * @param update the telegram message
   * @param method
   * @return false if this update should not be handled by this method. True if you don't mind.
   */
  boolean isMatch(Update update, MappingDefinition method);

}