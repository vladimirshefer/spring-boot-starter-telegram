package io.github.vladimirshefer.spring.chatbots.argument.resolver;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerMethodDefinition;
import io.github.vladimirshefer.spring.chatbots.core.messaging.ArgumentResolver;
import io.github.vladimirshefer.spring.chatbots.method_filter.MethodFilter;

public abstract class FilteringArgumentResolver implements ArgumentResolver, MethodFilter {

  public final boolean isMatch(EventFacade event, HandlerMethodDefinition method) {
    boolean noArgumentsToResolve = method.getArguments().stream().noneMatch(this::shouldResolve);
    if (noArgumentsToResolve) return true;

    boolean updateHasValue = hasValue(event);
    return method.getArguments()
      .stream()
      .noneMatch(argument -> valueIsRequired(argument) && !updateHasValue);
  }

  protected abstract boolean hasValue(EventFacade event);

  protected abstract boolean valueIsRequired(HandlerArgumentDefinition argument);

}
