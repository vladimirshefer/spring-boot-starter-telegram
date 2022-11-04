package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.argument_resolvers.ArgumentResolver;
import io.github.vladimirshefer.spring.chatbots.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.method_filter.SimpleMethodFilter;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class FilteringArgumentResolver extends SimpleMethodFilter implements ArgumentResolver {

  @Override
  public abstract Object resolve(HandlerArgumentDefinition argument, Update update);

}
