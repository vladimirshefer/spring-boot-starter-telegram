package io.github.vladimirshefer.spring.chatbots.argument_resolvers;

import io.github.vladimirshefer.spring.chatbots.handler.HandlerMethodDefinition;

import javax.annotation.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Class to set arguments when calling update handler method in bot controller.
 */
public interface ArgumentResolver {

  /**
   * Retrieves required information for the handler parameter from the update.
   * @param method The full information about handler method.
   * @param update The incoming update (i.e. telegram message)
   * @param index The index of the handler method parameter to resolve.
   * @return the value for argument. null if this handler should not or could not set the value.
   */
  @Nullable
  Object resolve(
    HandlerMethodDefinition method,
    Update update,
    int index
  );

}
