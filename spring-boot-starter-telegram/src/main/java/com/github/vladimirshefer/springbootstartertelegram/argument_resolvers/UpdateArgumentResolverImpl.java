package com.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateArgumentResolverImpl implements ArgumentResolver {

  public void resolve(
    MappingDefinition mappingDefinition,
    Update update,
    Object[] result,
    int index
  ) {
    Class<?>[] parameterTypes = mappingDefinition.getOriginalMethod().getParameterTypes();
    if (parameterTypes[index].equals(Update.class)) {
      result[index] = update;
    }
  }

}
