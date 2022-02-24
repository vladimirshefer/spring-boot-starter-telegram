package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getMessageTextOrNull;

import io.github.vladimirshefer.springbootstartertelegram.annotations.MessageText;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageTextAnnotationArgumentResolverImpl implements ArgumentResolver {

  @Override
  public Object resolve(
    HandlerMethodDefinition method,
    Update update,
    int index
  ) {
    if (method.getArgument(index).getAnnotations().contains(MessageText.class)){
      return getMessageTextOrNull(update);
    }

    return null;
  }

}
