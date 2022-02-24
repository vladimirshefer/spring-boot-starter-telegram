package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getPollOrNull;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

@Component
public class PollArgumentResolverImpl implements ArgumentResolver {

  public Object resolve(
    MappingDefinition method,
    Update update,
    int index
  ) {
    Class<?> parameterType = getParameterType(method, index);
    if (parameterType.equals(Poll.class)) {
      return getPollOrNull(update);
    }
    return null;
  }

  private Class<?> getParameterType(MappingDefinition method, int index) {
    Class<?>[] parameterTypes = method.getOriginalMethod().getParameterTypes();
    return parameterTypes[index];
  }

}
