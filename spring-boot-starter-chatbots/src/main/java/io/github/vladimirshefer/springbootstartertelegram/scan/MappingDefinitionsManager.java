package io.github.vladimirshefer.springbootstartertelegram.scan;

import io.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import io.github.vladimirshefer.springbootstartertelegram.handler.ControllerInvocationMethodsResolver;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MappingDefinitionsManager {

  private final List<HandlerMethodDefinition> methods = new ArrayList<>();
  private final ControllerInvocationMethodsResolver controllerInvocationMethods;

  public void registerController(String controllerName, Class<?> controllerClass,
                                 Object controllerBean) {
    List<HandlerMethodDefinition> controllerMethods =
      getControllerMethods(controllerName, controllerClass, controllerBean);

    methods.addAll(controllerMethods);
  }

  public List<HandlerMethodDefinition> findMappingDefinition(Update update) {
    return controllerInvocationMethods.getMethods(update, methods);
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
