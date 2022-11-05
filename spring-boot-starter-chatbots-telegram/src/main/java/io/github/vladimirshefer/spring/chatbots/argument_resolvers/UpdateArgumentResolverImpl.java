package io.github.vladimirshefer.spring.chatbots.argument_resolvers;

import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerArgumentDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateArgumentResolverImpl implements TelegramArgumentResolver {

  @Override
  public Object resolve(HandlerArgumentDefinition argument, Update update) {
    if (argument.getType().equals(Update.class)) {
      return update;
    }

    return null;
  }

}
