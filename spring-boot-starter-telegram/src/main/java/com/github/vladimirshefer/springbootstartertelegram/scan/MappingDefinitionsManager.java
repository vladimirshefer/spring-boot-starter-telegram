package com.github.vladimirshefer.springbootstartertelegram.scan;

import com.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

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
    String text = Optional.ofNullable(update)
        .map(Update::getMessage)
        .map(Message::getText)
        .orElse(null);

    if (text == null) {
      return Optional.empty();
    }

    return mappingDefinitions.stream()
        .filter(it -> text.startsWith(it.getRequestMappingValue()))
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
