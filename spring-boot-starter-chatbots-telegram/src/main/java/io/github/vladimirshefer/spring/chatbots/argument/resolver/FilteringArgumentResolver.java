package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.argument_resolvers.TelegramArgumentResolver;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.method_filter.SimpleMethodFilter;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class FilteringArgumentResolver extends SimpleMethodFilter implements TelegramArgumentResolver {

  @Override
  public abstract Object resolve(HandlerArgumentDefinition argument, Update update);

}
