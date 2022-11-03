package io.github.vladimirshefer.springbootstartertelegram.argument.resolver;

import io.github.vladimirshefer.springbootstartertelegram.argument_resolvers.ArgumentResolver;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import io.github.vladimirshefer.springbootstartertelegram.method_filter.SimpleMethodFilter;
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
