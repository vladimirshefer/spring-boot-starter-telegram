package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.argument_resolvers.ArgumentResolver;
import io.github.vladimirshefer.spring.chatbots.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.handler.HandlerMethodDefinition;
import io.github.vladimirshefer.spring.chatbots.method_filter.SimpleMethodFilter;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.Nullable;

public abstract class FilteringArgumentResolver extends SimpleMethodFilter implements ArgumentResolver {

  @Nullable
  @Override
  public final Object resolve(HandlerMethodDefinition method, Update update, int index) {
    return resolve(method.getArgument(index), update);
  }

  protected abstract Object resolve(HandlerArgumentDefinition argument, Update update);

}
