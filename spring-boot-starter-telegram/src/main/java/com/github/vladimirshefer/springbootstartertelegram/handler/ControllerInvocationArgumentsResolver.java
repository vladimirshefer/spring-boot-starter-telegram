package com.github.vladimirshefer.springbootstartertelegram.handler;

import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getMessageTextOrNull;
import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getPhotoOrNull;

import com.github.vladimirshefer.springbootstartertelegram.annotations.MessageBody;
import com.github.vladimirshefer.springbootstartertelegram.argument_resolvers.ArgumentResolver;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
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

    // I think it`s not good-looking, maybe I do with it
    Type[] genericParameterTypes = mappingDefinition.getOriginalMethod().getGenericParameterTypes();
    for (int i = 0; i < genericParameterTypes.length; i++) {
      if (genericParameterTypes[i].getTypeName().contains("PhotoSize")) {
        parametersArray[i] = getPhotoOrNull(update);
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
