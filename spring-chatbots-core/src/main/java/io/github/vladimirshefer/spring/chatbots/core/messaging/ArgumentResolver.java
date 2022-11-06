package io.github.vladimirshefer.spring.chatbots.core.messaging;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerArgumentDefinition;

/**
 * Class to set arguments when calling update handler method in bot controller.
 */
public interface ArgumentResolver {

  default boolean shouldResolve(HandlerArgumentDefinition argument) {
    return true;
  }

  /**
   * Retrieves required information for the handler parameter from the update.
   * @param argument information about handler argument.
   * @param event The incoming event (i.e. telegram message sent)
   * @return the value for argument. null if this handler should not or could not set the value.
   */
  Object resolve(HandlerArgumentDefinition argument, EventFacade event);

}
