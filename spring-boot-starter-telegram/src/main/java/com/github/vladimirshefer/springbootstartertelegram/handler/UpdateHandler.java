package com.github.vladimirshefer.springbootstartertelegram.handler;

import com.github.vladimirshefer.springbootstartertelegram.scan.MappingDefinitionsManager;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getChatId;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

  private final MappingDefinitionsManager mappingDefinitionsManager;
  private final ControllerInvocationArgumentsResolver controllerInvocationArgumentsResolver;

  public BotApiMethod<?>[] handleMessage(
          Update update
  ) {
    List<MappingDefinition> listOfMappingDefinitions = mappingDefinitionsManager.findMappingDefinition(update);
    if (listOfMappingDefinitions.isEmpty()) {
      return null;
    }

    return invokeHandler(listOfMappingDefinitions, update);
  }

  private SendMessage sendSimpleMessage(Update update, String result) {
    return new SendMessage(getChatId(update), result);
  }

  @SneakyThrows
  private BotApiMethod<?>[] invokeHandler(List<MappingDefinition> listOfMapingDefinitions, Update update) {
    Object[] results = new Object[listOfMapingDefinitions.size()];
    BotApiMethod<?>[] answers = new BotApiMethod[listOfMapingDefinitions.size()];
    for (int i = 0; i < listOfMapingDefinitions.size(); i++) {
      MappingDefinition mappingDefinition = listOfMapingDefinitions.get(i);
      Object[] arguments = getArgumentsForControllerMethodInvocation(mappingDefinition, update);
      results[i] = mappingDefinition.getTargetMethod().invoke(mappingDefinition.getController(), arguments);

      if (results[i] == null) {
        answers[i] = null;
        continue;
      }

      if (results[i] instanceof String) {
        answers[i] = sendSimpleMessage(update, (String) results[i]);
      } else {
        answers[i] = sendSimpleMessage(update, results[i].toString());
      }

    }
    return answers;
  }

  public Object[] getArgumentsForControllerMethodInvocation(
          MappingDefinition mappingDefinition,
          Update update
  ) {
    return controllerInvocationArgumentsResolver.getArguments(mappingDefinition, update);
  }


}
