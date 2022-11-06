package io.github.vladimirshefer.spring.chatbots.core.engine;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.resolvers.MethodFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ControllerInvocationMethodsResolver {

  private final List<MethodFilter> methodFilters;

  public List<HandlerMethodDefinition> getMethods(EventFacade event, List<HandlerMethodDefinition> methods) {
    return methods.stream()
            .filter(method -> methodFilters.stream().allMatch(filter -> filter.isMatch(event, method)))
            .collect(Collectors.toList());
  }
}