package io.github.vladimirshefer.spring.chatbots.argument_resolvers;

import io.github.vladimirshefer.spring.chatbots.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateArgumentResolverImpl implements ArgumentResolver {

  @Override
  public Object resolve(HandlerArgumentDefinition argument, Update update) {
    if (argument.getType().equals(Update.class)) {
      return update;
    }

    return null;
  }

}
