package io.github.vladimirshefer.spring.chatbots.argument_resolvers;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.core.messaging.ArgumentResolver;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Class to set arguments when calling update handler method in bot controller.
 */
public interface TelegramArgumentResolver extends ArgumentResolver {

  default boolean shouldResolve(HandlerArgumentDefinition argumentDefinition) {
    return true;
  }

  /**
   * Retrieves required information for the handler parameter from the update.
   * @param argument information about handler argument.
   * @param update The incoming update (i.e. telegram message)
   * @return the value for argument. null if this handler should not or could not set the value.
   */
  Object resolve(HandlerArgumentDefinition argument, Update update);

  /**
   * Retrieves required information for the handler parameter from the update.
   * @param argument information about handler argument.
   * @param event The incoming event (i.e. telegram message sent)
   * @return the value for argument. null if this handler should not or could not set the value.
   */
  default Object resolve(HandlerArgumentDefinition argument, EventFacade event) {
    Object source = event.getSource();

    if (source instanceof Update) {
      return resolve(argument, (Update) source);
    }

    return null;
  }

}
