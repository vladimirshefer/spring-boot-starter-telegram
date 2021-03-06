package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getPollOrNull;

import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

@Component
public class PollArgumentResolverImpl implements ArgumentResolver {

  public Object resolve(
    HandlerMethodDefinition method,
    Update update,
    int index
  ) {
    Class<?> parameterType = method.getArgument(index).getType();
    if (parameterType.equals(Poll.class)) {
      return getPollOrNull(update);
    }
    return null;
  }

}
