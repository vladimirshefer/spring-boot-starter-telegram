package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StringArgumentResolverImpl implements ArgumentResolver {

  @Override
  public Object resolve(
    HandlerMethodDefinition method,
    Update update,
    int index
  ) {
    HandlerArgumentDefinition argument = method.getArgument(index);

    if (argument.getType().equals(String.class) && argument.getAnnotations().isEmpty()) {
      return UpdateUtil.getMessageTextOrNull(update);
    }

    return null;
  }
}
