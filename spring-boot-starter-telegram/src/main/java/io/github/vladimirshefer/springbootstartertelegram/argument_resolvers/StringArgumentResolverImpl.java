package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

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
      String messageText = UpdateUtil.getMessageTextOrNull(update);
      if (messageText != null) {
        result[index] = messageText;
      }
    }
  }
}
