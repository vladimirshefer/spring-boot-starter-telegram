package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ArgumentResolver {

  void resolve(
    MappingDefinition mappingDefinition,
    Update update,
    Object[] result,
    int index
  );

}
