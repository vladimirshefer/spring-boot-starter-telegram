package com.github.vladimirshefer.springbootstartertelegram.handler;

import com.github.vladimirshefer.springbootstartertelegram.method_filter.MethodFilter;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ControllerInvocationMethods {

  private final List<MethodFilter> methodFilters;

  public List<MappingDefinition> getMethods(Update update, List<MappingDefinition> mappingDefinitions) {
    List<MappingDefinition> listOfMethods = new ArrayList<>();

    for (MappingDefinition method : mappingDefinitions) {
      for (MethodFilter methodFilter: methodFilters){
        if(methodFilter.isMatch(update, method)){
          listOfMethods.add(method);
          break;
        }
      }
    }

    return listOfMethods;
  }
}
