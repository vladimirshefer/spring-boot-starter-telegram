package io.github.vladimirshefer.springbootstartertelegram.handler;

import io.github.vladimirshefer.springbootstartertelegram.method_filter.MethodFilter;
import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ControllerInvocationMethodsResolver {

  private final List<MethodFilter> methodFilters;

  public List<MappingDefinition> getMethods(Update update, List<MappingDefinition> methods) {
    return methods.stream()
            .filter(method -> methodFilters.stream().allMatch(filter -> filter.isMatch(update, method)))
            .collect(Collectors.toList());
  }
}
