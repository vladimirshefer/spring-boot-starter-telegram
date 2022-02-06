package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getPollOrNull;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

@Component
public class PollArgumentResolverImpl implements ArgumentResolver {

  public void resolve(
    MappingDefinition mappingDefinition,
    Update update,
    Object[] result,
    int index
  ) {
    Class<?>[] parameterTypes = mappingDefinition.getOriginalMethod().getParameterTypes();
    if (parameterTypes[index].equals(Poll.class)) {
      Poll poll = getPollOrNull(update);
      if (poll != null) {
        result[index] = poll;
      }
    }
  }

}
