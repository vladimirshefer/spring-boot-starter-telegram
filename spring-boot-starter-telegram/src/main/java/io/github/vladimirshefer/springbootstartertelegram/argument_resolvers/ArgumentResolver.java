package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ArgumentResolver {

  default void resolve(
    MappingDefinition mappingDefinition,
    Update update,
    Object[] result,
    int index
  ) {
    result[index] = this.resolve(mappingDefinition, update, index);
  }

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
