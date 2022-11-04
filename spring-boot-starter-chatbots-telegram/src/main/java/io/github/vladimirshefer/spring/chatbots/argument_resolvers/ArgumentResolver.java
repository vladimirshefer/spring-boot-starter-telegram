package io.github.vladimirshefer.spring.chatbots.argument_resolvers;

import io.github.vladimirshefer.spring.chatbots.handler.HandlerArgumentDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Class to set arguments when calling update handler method in bot controller.
 */
public interface ArgumentResolver {

  /**
   * Retrieves required information for the handler parameter from the update.
   * @param argument information about handler argument.
   * @param update The incoming update (i.e. telegram message)
   * @return the value for argument. null if this handler should not or could not set the value.
   */
  Object resolve(HandlerArgumentDefinition argument, Update update);

}
