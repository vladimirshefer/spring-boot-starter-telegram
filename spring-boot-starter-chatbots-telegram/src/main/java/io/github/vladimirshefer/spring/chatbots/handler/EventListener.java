package io.github.vladimirshefer.spring.chatbots.handler;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerMethodDefinition;
import io.github.vladimirshefer.spring.chatbots.core.service.ControllerInvocationArgumentsResolver;
import io.github.vladimirshefer.spring.chatbots.scan.MappingDefinitionsManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventListener {

  private final MappingDefinitionsManager mappingDefinitionsManager;
  private final ControllerInvocationArgumentsResolver controllerInvocationArgumentsResolver;

  public List<Object> handleMessage(EventFacade event) {
    List<HandlerMethodDefinition> methods = mappingDefinitionsManager
      .findMappingDefinition(event);

    return Collections.unmodifiableList(invokeHandlers(methods, event));
  }

  @SneakyThrows
  private List<Object> invokeHandlers(
    List<HandlerMethodDefinition> methods,
    EventFacade event
  ) {
    return methods.stream()
      .map(mappingDefinition -> invokeController(mappingDefinition, event))
      .filter(Objects::nonNull)
      .collect(Collectors.toList());
  }


  @SneakyThrows
  private Object invokeController(HandlerMethodDefinition mappingDefinition, EventFacade event) {
    Object[] arguments = getArgumentsForControllerMethodInvocation(mappingDefinition, event);
    return mappingDefinition.getTargetMethod().invoke(mappingDefinition.getController(), arguments);
  }

  public Object[] getArgumentsForControllerMethodInvocation(
    HandlerMethodDefinition mappingDefinition,
    EventFacade event
  ) {
    return controllerInvocationArgumentsResolver.getArguments(mappingDefinition, event);
  }

}
