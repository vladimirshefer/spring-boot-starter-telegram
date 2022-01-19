package com.github.vladimirshefer.springbootstartertelegram.handler;

import com.github.vladimirshefer.springbootstartertelegram.scan.MappingDefinitionsManager;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

  private final MappingDefinitionsManager mappingDefinitionsManager;

  public void handleMessage(Update update) {
    MappingDefinition mappingDefinition = mappingDefinitionsManager.findMappingDefinition(update).orElse(null);
    if (mappingDefinition == null) {
      return;
    }

    Object result = invokeHandler(mappingDefinition, update);
  }

  @SneakyThrows
  private Object invokeHandler(MappingDefinition mappingDefinition, Update update) {
    Object[] arguments = getArgumentsForControllerMethodInvocation(
        mappingDefinition, update);
    return mappingDefinition.getTargetMethod().invoke(mappingDefinition.getController(), arguments);
  }

  private Object[] getArgumentsForControllerMethodInvocation(
      MappingDefinition mappingDefinition,
      Update update
  ) {
    int parameterCount = mappingDefinition.getOriginalMethod().getParameterCount();
    return IntStream.range(0, parameterCount).mapToObj(it -> null).toArray();
    // TODO
  }


}
