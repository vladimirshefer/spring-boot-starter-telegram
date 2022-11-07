package io.github.vladimirshefer.spring.chatbots.core.engine;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.resolvers.MethodFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ControllerInvocationMethodsResolver {

  private final List<MethodFilter> methodFilters;

  private final Map<HandlerMethodDefinition, List<MethodFilter>> methodFiltersCache =
    new HashMap<>();

  public List<HandlerMethodDefinition> getMethods(EventFacade event, List<HandlerMethodDefinition> methods) {
    List<HandlerMethodDefinition> list = new ArrayList<>();
    for (HandlerMethodDefinition method : methods) {
      List<MethodFilter> methodFiltersCashed = methodFiltersCache.get(method);
      if (methodFiltersCashed == null) {
        methodFiltersCashed = methodFilters.stream()
          .filter(mf -> mf.isCompatible(method))
          .collect(Collectors.toList());
        methodFiltersCache.put(method, methodFiltersCashed);
      }
      if (methodFiltersCashed.stream().allMatch(filter -> filter.isMatch(event, method))) {
        list.add(method);
      }
    }
    return list;
  }
}
