package com.github.vladimirshefer.springbootstartertelegram.handler;

import com.github.vladimirshefer.springbootstartertelegram.method_filter.MethodFilter;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ControllerInvocationMethodsResolver {

  private final List<MethodFilter> methodFilters;

  public List<MappingDefinition> getMethods(Update update, List<MappingDefinition> mappingDefinitions) {
    return mappingDefinitions.stream()
            .filter(method -> methodFilters.stream().anyMatch(filter -> !filter.isMatch(update, method)))
            .collect(Collectors.toList());
  }
}
