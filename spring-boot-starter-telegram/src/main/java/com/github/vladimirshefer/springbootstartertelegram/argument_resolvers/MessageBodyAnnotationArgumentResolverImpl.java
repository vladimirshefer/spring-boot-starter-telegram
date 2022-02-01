package com.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import static com.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getMessageTextOrNull;

import com.github.vladimirshefer.springbootstartertelegram.annotations.MessageBody;
import com.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.lang.annotation.Annotation;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageBodyAnnotationArgumentResolverImpl implements ArgumentResolver {

  @Override
  public void resolve(
    MappingDefinition mappingDefinition,
    Update update,
    Object[] result,
    int index
  ) {
    Annotation[] parameterAnnotations = mappingDefinition
      .getOriginalMethod()
      .getParameterAnnotations()[index];

    for (Annotation parameterAnnotation : parameterAnnotations) {
      if (parameterAnnotation.annotationType().equals(MessageBody.class)) {
        result[index] = getMessageTextOrNull(update);
      }
    }

  }

}
