package com.github.vladimirshefer.springbootstartertelegram.handler;

import com.github.vladimirshefer.springbootstartertelegram.argument_resolvers.ArgumentResolver;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ControllerInvocationArgumentsResolver {

  private final List<ArgumentResolver> argumentResolvers;

  public Object[] getArguments(
    MappingDefinition mappingDefinition,
    Update update
  ) {
    int parameterCount = mappingDefinition.getOriginalMethod().getParameterCount();
    Object[] parametersArray = new Object[parameterCount];

    int parametersAmount = mappingDefinition.getOriginalMethod().getParameters().length;

    for (int i = 0; i < parametersAmount; i++) {
      int index = i;
      argumentResolvers.forEach(
        it -> it.resolve(mappingDefinition, update, parametersArray, index));
    }

    return parametersArray;
  }

}
