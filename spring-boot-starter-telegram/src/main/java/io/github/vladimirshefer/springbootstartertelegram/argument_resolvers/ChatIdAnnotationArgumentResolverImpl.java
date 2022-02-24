package io.github.vladimirshefer.springbootstartertelegram.argument_resolvers;

import io.github.vladimirshefer.springbootstartertelegram.annotations.ChatId;
import io.github.vladimirshefer.springbootstartertelegram.handler.HandlerMethodDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;

import static io.github.vladimirshefer.springbootstartertelegram.telegram.util.UpdateUtil.getChatId;

@Component
public class ChatIdAnnotationArgumentResolverImpl implements ArgumentResolver {

  @Override
  public Object resolve(
    HandlerMethodDefinition method,
    Update update,
    int index
  ) {
    if (method.getArgument(index).getAnnotations().contains(ChatId.class)) {
      return getChatId(update);
    }

    return null;
  }

}
