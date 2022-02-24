package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateArgumentResolverImpl implements ArgumentResolver {

  public Object resolve(
    HandlerMethodDefinition method,
    Update update,
    int index
  ) {
    Class<?>[] parameterTypes = method.getOriginalMethod().getParameterTypes();

    if (parameterTypes[index].equals(Update.class)) {
      return update;
    }

    return null;
  }

}
