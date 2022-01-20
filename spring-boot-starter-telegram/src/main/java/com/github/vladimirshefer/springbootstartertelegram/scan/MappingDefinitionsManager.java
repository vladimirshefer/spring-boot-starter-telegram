package com.github.vladimirshefer.springbootstartertelegram.scan;

import com.github.vladimirshefer.springbootstartertelegram.annotations.RequestMapping;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

/*если у нас нет никаких параметров у аннотации мы идем искать методы в которые мым можем положить
   значения по сигнатуре  */
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
    /* и тут проверять если необходимые параметры в методе */
    if (UpdateUtil.getPollOrNull(update) != null) {
      mappingDefinitionStream = mappingDefinitionStream.filter(method ->
        Arrays.asList(method.getTargetMethod().getParameterTypes()).contains(Poll.class));
    }
    if (UpdateUtil.getPhotoOrNull(update) != null) {
      mappingDefinitionStream = mappingDefinitionStream.filter(method ->
        Arrays.asList(method.getTargetMethod().getParameterTypes()).contains(PhotoSize.class));
    }

    List<MappingDefinition> listOfMappingDefinitions = mappingDefinitionStream.collect(Collectors.toList());
/*надо еще как до добаить типа если ничего не нашли то пытаться искать методы с параметрами Update или
 Message или метод с пустой сигнатурой*/
    if (listOfMappingDefinitions.size() == 1) {
      return Optional.ofNullable(listOfMappingDefinitions.get(0));
    }
    return Optional.empty();
  }

  private Optional<MappingDefinition> findDefinitionForText(String text, Update update) {
    Optional<MappingDefinition> definitionByRegex = findDefinitionByRegex(text);

    if (definitionByRegex.isPresent()) {
      return definitionByRegex;
    }

    return findDefinitionByValue(text);
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
