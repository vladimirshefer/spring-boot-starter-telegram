package com.github.vladimirshefer.springbootstartertelegram.scan;

import com.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MappingDefinitionsManager {

  private final List<MappingDefinition> mappingDefinitions = new ArrayList<>();

  public void registerController(String controllerName, Class<?> controllerClass,
                                 Object controllerBean) {
    List<MappingDefinition> mappingDefinitionsForController = getMappingDefinitionsForController(
            controllerName, controllerClass, controllerBean);
    mappingDefinitions.addAll(mappingDefinitionsForController);
  }

  public Optional<MappingDefinition> findMappingDefinition(Update update) {
    String text = UpdateUtil.getMessageTextOrNull(update);

    if (text == null) {
      return Optional.empty();
    }

    Optional<MappingDefinition> definitionByRegex = findDefinitionByRegex(text);
    if (definitionByRegex.isPresent()) {
      return definitionByRegex;
    }

    return findDefinitionByValue(text);
  }

  private Optional<MappingDefinition> findDefinitionByRegex(String text) {
    return mappingDefinitions.stream()
            .filter(it -> !"".equals(it.getMappingAnnotation().regex()))
            .filter(it -> text.matches(it.getMappingAnnotation().regex()))
            .findAny();
  }

  private Optional<MappingDefinition> findDefinitionByValue(String text) {
    return mappingDefinitions.stream()
            .filter(it -> !"".equals(it.getMappingAnnotation().value()))
            .filter(it -> text.startsWith(it.getMappingAnnotation().value()))
            .findAny();
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
