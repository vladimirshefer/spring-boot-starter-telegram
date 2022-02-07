package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getMessageTextOrNull;

import io.github.vladimirshefer.springbootstartertelegram.annotations.MessageText;
import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import java.lang.annotation.Annotation;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageTextAnnotationArgumentResolverImpl implements ArgumentResolver {

  @Override
  public Object resolve(
    MappingDefinition mappingDefinition,
    Update update,
    int index
  ) {
    Annotation[] parameterAnnotations = mappingDefinition
      .getOriginalMethod()
      .getParameterAnnotations()[index];

    for (Annotation parameterAnnotation : parameterAnnotations) {
      if (parameterAnnotation.annotationType().equals(MessageText.class)) {
        return getMessageTextOrNull(update);
      }
    }

    return null;
  }

}
