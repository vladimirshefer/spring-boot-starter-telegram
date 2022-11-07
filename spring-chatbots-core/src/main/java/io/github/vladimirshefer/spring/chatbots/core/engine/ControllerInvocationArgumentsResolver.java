package io.github.vladimirshefer.spring.chatbots.core.engine;

import io.github.vladimirshefer.spring.chatbots.core.facade.EventFacade;
import io.github.vladimirshefer.spring.chatbots.core.resolvers.ArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used to extract arguments required by handler (controller method).
 * The main source of arguments values is the Update from Telegram,
 * but implementations are allowed use any other datasource (e.g. database, api, files)
 * required for correct binding.
 */
@Component
@RequiredArgsConstructor
public class ControllerInvocationArgumentsResolver {

  private final List<ArgumentResolver> argumentResolvers;

  private final Map<HandlerArgumentDefinition, List<ArgumentResolver>> argumentResolversCache =
    new HashMap<>();

  /**
   * This method collects the values for arguments of controller method.
   * This method is called for each update for each controller method
   * which was not filtered out by MethodFilter implementations.
   *
   * @param mappingDefinition the information about handler method.
   * @param event
   * @return The array of the parameters for calling handler method.
   */
  public Object[] getArguments(
    HandlerMethodDefinition mappingDefinition,
    EventFacade event
  ) {
    int parameterCount = mappingDefinition.getOriginalMethod().getParameterCount();
    Object[] parametersArray = new Object[parameterCount];

    int parametersAmount = mappingDefinition.getOriginalMethod().getParameters().length;

    for (int i = 0; i < parametersAmount; i++) {
      HandlerArgumentDefinition argument = mappingDefinition.getArgument(i);
      List<ArgumentResolver> argumentResolversCached = argumentResolversCache.get(argument);
      if (argumentResolversCached == null) {
        argumentResolversCached = argumentResolvers.stream()
          .filter(ar -> ar.shouldResolve(argument))
          .collect(Collectors.toList());
        argumentResolversCache.put(argument, argumentResolversCached);
      }

      for (ArgumentResolver argumentResolver : argumentResolversCached) {
        Object value = argumentResolver.resolve(argument, event);
        if (value != null) {
          parametersArray[i] = value;
        }
      }
    }

    return parametersArray;
  }

}
