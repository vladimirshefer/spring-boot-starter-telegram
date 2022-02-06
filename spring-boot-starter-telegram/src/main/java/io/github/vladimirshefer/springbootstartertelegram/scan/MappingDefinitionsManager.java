package io.github.vladimirshefer.springbootstartertelegram.scan;

import io.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import io.github.vladimirshefer.springbootstartertelegram.handler.ControllerInvocationMethodsResolver;
import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
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

  private final List<MappingDefinition> mappingDefinitions = new ArrayList<>();
  private final ControllerInvocationMethodsResolver controllerInvocationMethods;

  public void registerController(String controllerName, Class<?> controllerClass,
                                 Object controllerBean) {
    List<MappingDefinition> mappingDefinitionsForController = getMappingDefinitionsForController(
      controllerName, controllerClass, controllerBean);
    mappingDefinitions.addAll(mappingDefinitionsForController);
  }

  public List<MappingDefinition> findMappingDefinition(Update update) {
    return controllerInvocationMethods.getMethods(update, mappingDefinitions);
  }

  private List<MappingDefinition> getMappingDefinitionsForController(
    String controllerName,
    Class<?> controllerClass,
    Object controllerBean
  ) {
    return Arrays.stream(controllerClass.getDeclaredMethods())
      .filter(it -> it.getAnnotation(RequestMapping.class) != null)
      .map(it -> MappingDefinition.of(controllerName, controllerClass, controllerBean, it))
      .collect(Collectors.toList());
  }

}
