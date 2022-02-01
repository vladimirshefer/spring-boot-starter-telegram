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
      int numberOfMethodParameter = method.getTargetMethod().getParameterTypes().length;
      //for empty method I need to think a bit more
//      if (numberOfMethodParameter == 0){
//        listOfMethods.add(method);
//        continue;
//      }

      int numberOfFoundParameterTypes = 0;

      for (MethodFilter methodFilter : methodFilters) {
        if (methodFilter.isMatch(update, method)) {
          numberOfFoundParameterTypes++;
        }
      }
      // here can put <= if we find some parameters which can identify like User
      if (numberOfFoundParameterTypes == numberOfMethodParameter) {
        listOfMethods.add(method);
      }
    }

    return listOfMethods;
  }
}
