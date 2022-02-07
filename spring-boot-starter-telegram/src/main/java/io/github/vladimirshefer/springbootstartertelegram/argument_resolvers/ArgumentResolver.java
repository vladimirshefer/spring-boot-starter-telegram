package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import javax.annotation.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ArgumentResolver {

  @Deprecated
  default void resolve(
    MappingDefinition mappingDefinition,
    Update update,
    Object[] result,
    int index
  ) {
    result[index] = this.resolve(mappingDefinition, update, index);
  }

  /**
   * Retrieves required information for the handler parameter from the update.
   * @param mappingDefinition The full information about handler method.
   * @param update The incoming update (i.e. telegram message)
   * @param index The index of the handler method parameter to resolve.
   * @return
   */
  @Nullable
  default Object resolve(
    MappingDefinition mappingDefinition,
    Update update,
    int index
  ){
    Object[] result = new Object[index + 1];
    this.resolve(mappingDefinition, update, result, index);
    return result[index];
  }

}
