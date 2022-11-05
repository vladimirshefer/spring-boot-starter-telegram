package io.github.vladimirshefer.spring.chatbots.method_filter;

import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerMethodDefinition;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerArgumentDefinition;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class SimpleMethodFilter implements MethodFilter {

  public final boolean isMatch(Update update, HandlerMethodDefinition method) {
    boolean updateHasValue = updateHasValue(update);
    return method.getArguments()
      .stream()
      .noneMatch(argument -> valueIsRequired(argument) && !updateHasValue);
  }

  protected abstract boolean updateHasValue(Update update);

  protected abstract boolean valueIsRequired(HandlerArgumentDefinition argument);

}
