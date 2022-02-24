package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.telegram.dto.MappingDefinition;
import io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;

@Component
public class StringArgumentResolverImpl implements ArgumentResolver {

  @Override
  public Object resolve(
    MappingDefinition mappingDefinition,
    Update update,
    int index
  ) {
    Annotation[] parameterAnnotations = mappingDefinition
      .getOriginalMethod()
      .getParameterAnnotations()[index];

    Class<?> parameterType = mappingDefinition
      .getOriginalMethod()
      .getParameterTypes()[index];

    if (parameterType.equals(String.class) && parameterAnnotations.length == 0) {
      return UpdateUtil.getMessageTextOrNull(update);
    }

    return null;
  }
}
