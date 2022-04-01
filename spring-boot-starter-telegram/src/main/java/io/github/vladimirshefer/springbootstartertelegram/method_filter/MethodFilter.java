package io.github.vladimirshefer.springbootstartertelegram.method_filter;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Gives ability to filter out controller methods if they are not appropriate for specific incoming
 * message.
 */
public interface MethodFilter {

  /**
   * @param update the telegram message
   * @param method the controller method information
   * @return false if this update should not be handled by this method. True if you don't mind.
   */
  boolean isMatch(Update update, HandlerMethodDefinition method);

}
