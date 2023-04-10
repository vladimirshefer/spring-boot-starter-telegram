package io.github.vladimirshefer.spring.chatbots.core.engine;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
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
    try {
      Object[] arguments = getArgumentsForControllerMethodInvocation(mappingDefinition, event);
      return mappingDefinition.getTargetMethod().invoke(mappingDefinition.getController(), arguments);
    } catch (Throwable throwable) {
      log.error("Event handler threw exception", throwable);
    }
    return null;
  }

  public Object[] getArgumentsForControllerMethodInvocation(
    HandlerMethodDefinition mappingDefinition,
    EventFacade event
  ) {
    return controllerInvocationArgumentsResolver.getArguments(mappingDefinition, event);
  }

}
