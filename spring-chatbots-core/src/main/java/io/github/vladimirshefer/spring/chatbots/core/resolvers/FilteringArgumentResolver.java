package io.github.vladimirshefer.spring.chatbots.core.resolvers;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.engine.HandlerArgumentDefinition;
import io.github.vladimirshefer.spring.chatbots.core.engine.HandlerMethodDefinition;

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
