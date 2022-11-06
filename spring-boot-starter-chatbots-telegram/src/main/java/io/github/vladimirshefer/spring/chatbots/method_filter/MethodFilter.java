package io.github.vladimirshefer.spring.chatbots.method_filter;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerMethodDefinition;
import io.github.vladimirshefer.spring.chatbots.telegram.facade.TelegramEventFacade;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Gives ability to filter out controller methods if they are not appropriate for specific incoming
 * message or event.
 */
public interface MethodFilter {

  /**
   * @param event the messenger event (i.e. message sent)
   * @param method the controller method information
   * @return false if this event should not be handled by this method. True if you don't mind.
   */
  boolean isMatch(EventFacade event, HandlerMethodDefinition method);

}
