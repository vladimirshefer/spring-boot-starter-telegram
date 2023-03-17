package io.github.vladimirshefer.spring.chatbots.core.resolvers;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.engine.HandlerMethodDefinition;

/**
 * Gives ability to filter out controller methods if they are not appropriate for specific incoming
 * message or event.
 */
public interface MethodFilter {

  /**
   * This method is always called after isCompatible method.
   * @param event the messenger event (i.e. message sent)
   * @param method the controller method information
   * @return false if this event should not be handled by this method. True if you don't mind.
   */
  boolean isMatch(EventFacade event, HandlerMethodDefinition method);

  default boolean isCompatible(HandlerMethodDefinition method) {
    return true;
  }

}
