package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StringArgumentResolverImpl implements ArgumentResolver {

  @Override
  public Object resolve(
    MappingDefinition mappingDefinition,
    Update update,
    int index
  ) {
    Class<?>[] parameterTypes = mappingDefinition.getOriginalMethod().getParameterTypes();
    if (parameterTypes[index].equals(String.class)) {
      return UpdateUtil.getMessageTextOrNull(update);
    }

    return null;
  }
}
