package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.annotations.ChatId;
import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getChatId;

@Component
public class ChatIdAnnotationArgumentResolverImpl implements ArgumentResolver {

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
      if (parameterAnnotation.annotationType().equals(ChatId.class)) {
        return getChatId(update);
      }
    }

    return null;
  }

}
