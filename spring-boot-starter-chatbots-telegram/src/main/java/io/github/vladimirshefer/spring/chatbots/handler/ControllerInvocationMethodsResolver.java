package io.github.vladimirshefer.spring.chatbots.handler;

import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerMethodDefinition;
import io.github.vladimirshefer.spring.chatbots.method_filter.MethodFilter;
import io.github.vladimirshefer.spring.chatbots.telegram.facade.TelegramEventFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ControllerInvocationMethodsResolver {

  private final List<MethodFilter> methodFilters;

  public List<HandlerMethodDefinition> getMethods(TelegramEventFacade event, List<HandlerMethodDefinition> methods) {
    return methods.stream()
            .filter(method -> methodFilters.stream().allMatch(filter -> filter.isMatch(event, method)))
            .collect(Collectors.toList());
  }
}
