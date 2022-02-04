package com.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getMessageTextOrNull;

@Component
public class StringArgumentResolverImpl implements ArgumentResolver {
  @Override
  public void resolve(
    MappingDefinition mappingDefinition,
    Update update,
    Object[] result,
    int index) {
    Class<?>[] parameterTypes = mappingDefinition.getOriginalMethod().getParameterTypes();
    if (parameterTypes[index].equals(String.class)) {
      String messageText = getMessageTextOrNull(update);
      if (messageText != null) {
        result[index] = messageText;
      }
    }
  }
}
