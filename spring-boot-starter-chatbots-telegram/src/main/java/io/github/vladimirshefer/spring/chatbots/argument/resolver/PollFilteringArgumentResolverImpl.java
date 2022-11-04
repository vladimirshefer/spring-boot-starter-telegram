package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.util.Optional;

import static io.github.vladimirshefer.spring.chatbots.telegram.util.UpdateUtil.getPollOrNull;

@Component
public class PollFilteringArgumentResolverImpl extends FilteringArgumentResolver {

  public Object resolve(
    HandlerArgumentDefinition argument,
    Update update
  ) {
    Class<?> parameterType = argument.getType();
    if (parameterType.equals(Poll.class)) {
      return getPollOrNull(update);
    }
    return null;
  }

  @Override
  public boolean updateHasValue(Update update) {
    return Optional.ofNullable(update).map(Update::getMessage).map(Message::getPoll).isPresent();
  }

  @Override
  protected boolean valueIsRequired(HandlerArgumentDefinition argument) {
    return argument.getType().equals(Poll.class);
  }

}
