package io.github.vladimirshefer.springbootstartertelegram.handler;

import io.github.vladimirshefer.springbootstartertelegram.scan.MappingDefinitionsManager;
import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

  private final MappingDefinitionsManager mappingDefinitionsManager;
  private final ControllerInvocationArgumentsResolver controllerInvocationArgumentsResolver;

  public List<BotApiMethod<?>> handleMessage(Update update) {
    List<MappingDefinition> mappingDefinitions = mappingDefinitionsManager
        .findMappingDefinition(update);

    return invokeHandlers(mappingDefinitions, update);
  }

  private SendMessage sendSimpleMessage(Update update, String result) {
    SendMessage sendMessage = new SendMessage(UpdateUtil.getChatId(update), result);
    if (update.getMessage() != null && update.getMessage().getMessageId() != null) {
      sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
    }
    return sendMessage;
  }

  @SneakyThrows
  private List<BotApiMethod<?>> invokeHandlers(
      List<MappingDefinition> mappingDefinitions,
      Update update
  ) {
    return mappingDefinitions.stream()
        .map(mappingDefinition -> invokeController(update, mappingDefinition))
        .filter(Objects::nonNull)
        .map(methodInvocationResult -> resultToApiMethod(update, methodInvocationResult))
        .collect(Collectors.toList());
  }

  private BotApiMethod<?> resultToApiMethod(Update update, Object methodInvocationResult) {
    if (methodInvocationResult instanceof BotApiMethod) {
      return (BotApiMethod<?>) methodInvocationResult;
    }

    return sendSimpleMessage(update, methodInvocationResult.toString());
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
