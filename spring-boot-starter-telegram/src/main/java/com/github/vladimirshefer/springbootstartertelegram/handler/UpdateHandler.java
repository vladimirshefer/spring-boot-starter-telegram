package com.github.vladimirshefer.springbootstartertelegram.handler;

import com.github.vladimirshefer.springbootstartertelegram.scan.MappingDefinitionsManager;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getChatId;
import static java.util.Collections.emptyList;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

  private final MappingDefinitionsManager mappingDefinitionsManager;
  private final ControllerInvocationArgumentsResolver controllerInvocationArgumentsResolver;

  public List<BotApiMethod<?>> handleMessage(Update update) {
    List<MappingDefinition> mappingDefinitions = mappingDefinitionsManager.findMappingDefinition(
        update);

    if (mappingDefinitions.isEmpty()) {
      return emptyList();
    }

    return invokeHandlers(mappingDefinitions, update);
  }

  private SendMessage sendSimpleMessage(Update update, String result) {
    return new SendMessage(getChatId(update), result);
  }

  @SneakyThrows
  private List<BotApiMethod<?>> invokeHandlers(
      List<MappingDefinition> mappingDefinitions,
      Update update
  ) {
    return mappingDefinitions.stream()
        .map(mappingDefinition -> invokeController(update, mappingDefinition))
        .filter(Objects::nonNull)
        .map(methodInvocationResult -> sendSimpleMessage(update, methodInvocationResult.toString()))
        .collect(Collectors.toList());
  }

  @SneakyThrows
  private Object invokeController(Update update, MappingDefinition mappingDefinition) {
    Object[] arguments = getArgumentsForControllerMethodInvocation(mappingDefinition, update);
    return mappingDefinition.getTargetMethod().invoke(mappingDefinition.getController(), arguments);
  }

  public Object[] getArgumentsForControllerMethodInvocation(
      MappingDefinition mappingDefinition,
      Update update
  ) {
    return controllerInvocationArgumentsResolver.getArguments(mappingDefinition, update);
  }


}
