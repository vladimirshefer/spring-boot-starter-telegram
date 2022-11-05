package io.github.vladimirshefer.spring.chatbots.handler;

import io.github.vladimirshefer.spring.chatbots.argument_resolvers.TelegramArgumentResolver;

import java.util.List;

import io.github.vladimirshefer.spring.chatbots.core.handler.HandlerMethodDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * This class is used to extract arguments required by handler (controller method).
 * The main source of arguments values is the Update from Telegram,
 * but implementations are allowed use any other datasource (e.g. database, api, files)
 * required for correct binding.
 *
 */
@Component
@RequiredArgsConstructor
public class ControllerInvocationArgumentsResolver {

  private final List<TelegramArgumentResolver> argumentResolvers;

  /**
   * This method collects the values for arguments of controller method.
   * This method is called for each update for each controller method
   * which was not filtered out by MethodFilter implementations.
   *
   * @param mappingDefinition the information about handler method
   * @param update the incoming telegram update (message)
   * @return The array of the parameters for calling handler method.
   */
  public Object[] getArguments(
    HandlerMethodDefinition mappingDefinition,
    Update update
  ) {
    int parameterCount = mappingDefinition.getOriginalMethod().getParameterCount();
    Object[] parametersArray = new Object[parameterCount];

    int parametersAmount = mappingDefinition.getOriginalMethod().getParameters().length;

    for (int i = 0; i < parametersAmount; i++) {
      for (TelegramArgumentResolver argumentResolver : argumentResolvers) {
        Object value = argumentResolver.resolve(mappingDefinition.getArgument(i), update);
        if (value != null) {
          parametersArray[i] = value;
        }
      }
    }

    return parametersArray;
  }

}
