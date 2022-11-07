package io.github.vladimirshefer.spring.chatbots.core.resolvers;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.engine.HandlerArgumentDefinition;

/**
 * Class to set arguments when calling update handler method in bot controller.
 */
public interface ArgumentResolver {

  /**
   * Check if this resolver is applicable for argument.
   * Return false, if this resolver should never be applied for this argument.
   * Return true, if resolver is compatible with argument.
   * If you are not sure, then return true.
   * This method should be idempotent. This means that for the same argument the result should be the same.
   * @param argument the argument of event handler.
   * @return false if not applicable. true otherwise.
   */
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
