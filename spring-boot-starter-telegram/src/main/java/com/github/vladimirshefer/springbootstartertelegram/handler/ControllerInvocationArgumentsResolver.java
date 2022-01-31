package com.github.vladimirshefer.springbootstartertelegram.handler;

import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getMessageTextOrNull;
import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getPhotoOrNull;
import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getPollOrNull;

import com.github.vladimirshefer.springbootstartertelegram.annotations.MessageBody;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

@Component
public class ControllerInvocationArgumentsResolver {

  public Object[] getArguments(MappingDefinition mappingDefinition,
    Update update){
    int parameterCount = mappingDefinition.getOriginalMethod().getParameterCount();
    Object[] parametersArray = new Object[parameterCount];

    Class<?>[] parameterTypes = mappingDefinition.getOriginalMethod().getParameterTypes();

    for (int i = 0; i < parameterTypes.length; i++) {
      if (parameterTypes[i].equals(Update.class)) {
        parametersArray[i] = update;
      }
      if (parameterTypes[i].equals(Poll.class)){
        parametersArray[i] = getPollOrNull(update);
      }
    }

    // I think it`s not good-looking, maybe I do with it
    Type[] genericParameterTypes = mappingDefinition.getOriginalMethod().getGenericParameterTypes();
    for (int i1 = 0; i1 < genericParameterTypes.length; i1++) {
      if (genericParameterTypes[i1].getTypeName().contains("PhotoSize")){
        parametersArray[i1] = getPhotoOrNull(update);
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
