package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import javax.annotation.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;

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
    MappingDefinition method,
    Update update,
    int index
  );

}
