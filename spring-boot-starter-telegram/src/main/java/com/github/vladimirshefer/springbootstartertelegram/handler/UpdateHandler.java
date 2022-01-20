package com.github.vladimirshefer.springbootstartertelegram.handler;

import com.github.vladimirshefer.springbootstartertelegram.annotations.MessageBody;
import com.github.vladimirshefer.springbootstartertelegram.scan.MappingDefinitionsManager;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;

import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getChatId;
import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getMessageTextOrNull;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

  private final MappingDefinitionsManager mappingDefinitionsManager;

  public BotApiMethod<?> handleMessage(
          Update update
  ) {
    MappingDefinition mappingDefinition = mappingDefinitionsManager.findMappingDefinition(update)
            .orElse(null);
    if (mappingDefinition == null) {
      return null;
    }

    Object result = invokeHandler(mappingDefinition, update);

    if (result == null) {
      return null;
    }

    if (result instanceof String) {
      return sendSimpleMessage(update, (String) result);
    }

    return sendSimpleMessage(update, result.toString());
  }

  private SendMessage sendSimpleMessage(Update update, String result) {
    return new SendMessage(getChatId(update), result);
  }

  @SneakyThrows
  private Object invokeHandler(MappingDefinition mappingDefinition, Update update) {
    Object[] arguments = getArgumentsForControllerMethodInvocation(
            mappingDefinition, update);
    return mappingDefinition.getTargetMethod().invoke(mappingDefinition.getController(), arguments);
  }

  public Object[] getArgumentsForControllerMethodInvocation(
          MappingDefinition mappingDefinition,
          Update update
  ) {
    int parameterCount = mappingDefinition.getOriginalMethod().getParameterCount();
    Object[] parametersArray = new Object[parameterCount];

    Class<?>[] parameterTypes = mappingDefinition.getOriginalMethod().getParameterTypes();

    for (int i = 0; i < parameterTypes.length; i++) {
      if (parameterTypes[i].equals(Update.class)) {
        parametersArray[i] = update;
      }
    }

    Annotation[][] parameterAnnotationsArray = mappingDefinition.getOriginalMethod()
            .getParameterAnnotations();
    for (int i = 0; i < parameterAnnotationsArray.length; i++) {
      Annotation[] parameterAnnotations = parameterAnnotationsArray[i];
      for (Annotation parameterAnnotation : parameterAnnotations) {
        if (parameterAnnotation.annotationType().equals(MessageBody.class)) {
          parametersArray[i] = getMessageTextOrNull(update);
        }
      }
    }


    return parametersArray;
  }

}
