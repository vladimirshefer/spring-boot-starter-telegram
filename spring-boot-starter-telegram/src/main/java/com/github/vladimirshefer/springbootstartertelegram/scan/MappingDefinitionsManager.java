package com.github.vladimirshefer.springbootstartertelegram.scan;

import com.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    if (text != null) {
      return findDefinitionForText(text, update);
    } else {
      return findDefinitionForDefaultMapping(update);
    }

  }

  private Optional<MappingDefinition> findDefinitionForDefaultMapping(Update update) {
    Stream<MappingDefinition> mappingDefinitionStream = mappingDefinitions.stream()
      .filter(it -> it.getMappingAnnotation().regex().equals(""))
      .filter(it -> it.getMappingAnnotation().value().equals(""));
    if (UpdateUtil.getPollOrNull(update) != null) {
      mappingDefinitionStream = mappingDefinitionStream.filter(method ->
        Arrays.asList(method.getTargetMethod().getParameterTypes()).contains(Poll.class));
    }

    //I find this way for taking parameter from generic param
    if (UpdateUtil.getPhotoOrNull(update) != null) {
      mappingDefinitionStream = mappingDefinitionStream.filter(method ->
        Arrays.stream(method.getTargetMethod().getGenericParameterTypes())
          .anyMatch(p -> p.getTypeName().contains("PhotoSize")));
    }

    List<MappingDefinition> listOfMappingDefinitions = mappingDefinitionStream.collect(Collectors.toList());

    if (listOfMappingDefinitions.size() == 1) {
      return Optional.ofNullable(listOfMappingDefinitions.get(0));
    }
    if (listOfMappingDefinitions.size() == 0){
      return findDefinitionWithUpdate(update);
    }
    return Optional.empty();
  }

  private Optional<MappingDefinition> findDefinitionWithUpdate(Update update) {
    Stream<MappingDefinition> mappingDefinitionStream = mappingDefinitions.stream()
      .filter(it -> it.getMappingAnnotation().regex().equals(""))
      .filter(it -> it.getMappingAnnotation().value().equals(""));
    List<MappingDefinition> listOfUpdateMethod = mappingDefinitionStream
      .filter(method -> Arrays.asList(method.getTargetMethod().getParameterTypes()).contains(Update.class))
      .collect(Collectors.toList());

    if (listOfUpdateMethod.size() == 1){
      return Optional.ofNullable(listOfUpdateMethod.get(0));
    }

    if (listOfUpdateMethod.isEmpty()){
      return findDefinitionWithEmptyDescription(update);
    }

    return Optional.empty();
  }

  private Optional<MappingDefinition> findDefinitionWithEmptyDescription(Update update) {
    Stream<MappingDefinition> mappingDefinitionStream = mappingDefinitions.stream()
      .filter(it -> it.getMappingAnnotation().regex().equals(""))
      .filter(it -> it.getMappingAnnotation().value().equals(""));

    List<MappingDefinition> listOfMethodWithEmptyDefinition = mappingDefinitionStream
      .filter(method -> method.getTargetMethod().getParameterTypes().length == 0)
      .collect(Collectors.toList());

    if (listOfMethodWithEmptyDefinition.size() != 0){
      return Optional.ofNullable(listOfMethodWithEmptyDefinition.get(0));
    }

    return Optional.empty();

  }

  private Optional<MappingDefinition> findDefinitionForText(String text, Update update) {
    Optional<MappingDefinition> definitionByRegex = findDefinitionByRegex(text);

    if (definitionByRegex.isPresent()) {
      return definitionByRegex;
    }

    Optional<MappingDefinition> definitionByValue = findDefinitionByValue(text);
    if (definitionByValue.isPresent()){
      return definitionByValue;
    }

    return findDefinitionWithUpdate(update);
  }

  private Optional<MappingDefinition> findDefinitionByDefault() {
    return mappingDefinitions.stream()
      .filter(it -> "".equals(it.getMappingAnnotation().regex()))
      .filter(it -> "".equals(it.getMappingAnnotation().value()))
      .findAny();
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
