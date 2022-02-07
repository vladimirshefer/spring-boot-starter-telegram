package io.github.vladimirshefer.springbootstartertelegram.handler;

import io.github.vladimirshefer.springbootstartertelegram.argument_resolvers.ArgumentResolver;
import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
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
      for (ArgumentResolver argumentResolver : argumentResolvers) {
        parametersArray[i] = argumentResolver.resolve(mappingDefinition, update, i);
      }
    }

    return parametersArray;
  }

}
