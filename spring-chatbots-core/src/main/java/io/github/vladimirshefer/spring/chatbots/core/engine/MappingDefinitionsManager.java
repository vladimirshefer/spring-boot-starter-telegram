package io.github.vladimirshefer.spring.chatbots.core.engine;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.messaging.annotations.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MappingDefinitionsManager {

  private final List<HandlerMethodDefinition> methods = new ArrayList<>();
  private final ControllerInvocationMethodsResolver controllerInvocationMethods;

  public void registerController(
    String controllerName,
    Class<?> controllerClass,
    Object controllerBean
  ) {
    List<HandlerMethodDefinition> controllerMethods =
      getControllerMethods(controllerName, controllerClass, controllerBean);

    methods.addAll(controllerMethods);
  }

  public List<HandlerMethodDefinition> findMappingDefinition(EventFacade event) {
    return controllerInvocationMethods.getMethods(event, methods);
  }

  private List<HandlerMethodDefinition> getControllerMethods(
    String controllerName,
    Class<?> controllerClass,
    Object controllerBean
  ) {
    return Arrays.stream(controllerClass.getDeclaredMethods())
      .filter(it -> it.getAnnotation(RequestMapping.class) != null)
      .map(it -> HandlerMethodDefinition.of(controllerName, controllerClass, controllerBean, it))
      .collect(Collectors.toList());
  }

}
